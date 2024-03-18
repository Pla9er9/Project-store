<script lang="ts">
    export let projectId: string;

    import Avatar from "$components/Avatar.svelte";
    import DeleteButton from "$components/DeleteButton.svelte";
    import LoadingIndicator from "$components/LoadingIndicator.svelte";
    import fetchHttp from "$lib/fetchHttp";
    import type { InvitationDto } from "$lib/models/invitation/InvitationDto";
    import { alertStore } from "$lib/stores/alertStore";
    import { tokenStore } from "$lib/stores/tokenStore";
    import { get } from "svelte/store";

    let newInvitationInput = "";
    const token = get(tokenStore);

    let invitations: InvitationDto[] | null = null;
    async function loadInvitations(): Promise<void> {
        const res = await fetchHttp(`/project/${projectId}/invitations`, {
            token: token,
            server: true,
            redirecting: true,
        });
        if (!res?.ok) {
            alertStore.update((a) => {
                a.message = "Could not get project invitations";
                a.color = "red";
                return a;
            });
            invitations = [];
        } else {
            invitations = res!.body;
        }
    }

    async function sendInvitation() {
        if (token === undefined) return;
        if (newInvitationInput === "") {
            alertStore.update((a) => {
                a.color = "red";
                a.message = "Enter username";
                return a;
            });
            return;
        }
        const res = await fetchHttp(`/project/${projectId}/invitations`, {
            method: "post",
            body: JSON.stringify({
                username: newInvitationInput,
            }),
            token: token,
        });

        if (res?.status === 404) {
            alertStore.update((a) => {
                a.color = "red";
                a.message = "Could not find the user";
                return a;
            });
            return
        }

        if (!res?.ok) {
            alertStore.update((a) => {
                a.color = "red";
                a.message = "Could not send the invitation try later";
                return a;
            });
            return
        }

        if (res && res?.body !== "") {
            invitations = [...(<[]>invitations), res.body];
        }
        newInvitationInput = "";
    }

    async function deleteInvitation(invitationId: string) {
        if (token === undefined) return;
        await fetchHttp(`/project/${projectId}/invitations/${invitationId}`, {
            method: "delete",
            token: token,
        });
        if (invitations) {
            invitations = invitations?.filter((e) => e.id !== invitationId);
        }
    }

    loadInvitations();
</script>

<div id="invitations">
    <button on:click={() => sendInvitation()} style="float: right;"
        >Send invitation</button
    >
    <input
        bind:value={newInvitationInput}
        type="text"
        style="float: right;"
        placeholder="Enter username"
    />
    {#if !invitations}
        <LoadingIndicator />
    {:else if invitations === undefined || invitations.length === 0}
        <p>No invitations</p>
    {:else}
        {#each invitations as invitation}
            <div class="invitation">
                <Avatar username={invitation.invitedUsername} size="30px" />
                <p>{invitation.invitedUsername}</p>
                <small>{invitation.created.slice(0, 16)}</small>
                <DeleteButton
                    callback={() => deleteInvitation(invitation.id)}
                />
            </div>
        {/each}
    {/if}
</div>

<style lang="scss">
    button {
        width: 120px;
        height: 35px;
        border: solid 1px #313131;
        background-color: inherit;
        color: #fff;
        cursor: pointer;
        border-top-right-radius: 5px;
        border-bottom-right-radius: 5px;
    }

    input {
        height: 31px;
        border: solid 1px #313131;
        background-color: inherit;
        outline: none;
        color: gainsboro;
        text-indent: 12px;
        margin-bottom: 50px;
    }

    .invitation {
        max-width: 750px;
        width: 100%;
        height: 70px;
        display: flex;
        align-items: center;
        padding-left: 20px;
        border-top: solid 1px #424040;
        border-bottom: solid 1px #424040;

        p {
            font-size: 15px;
            text-align: center;
            margin-left: 18px;
            margin-right: 14px;
            font-family: sans-serif;
        }

        small {
            margin-left: auto;
            margin-right: 10px;
            color: rgb(110 110 110);
            font-family: "Franklin Gothic Medium", "Arial Narrow", Arial,
                sans-serif;
        }
    }
</style>
