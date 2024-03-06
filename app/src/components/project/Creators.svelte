<script lang="ts">
    import Avatar from "$components/Avatar.svelte";
    import fetchHttp from "$lib/fetchHttp";
    import type { ProjectDTO } from "$lib/models/project/ProjectDTO";
    import { alertStore } from "$lib/stores/alertStore";
    import { tokenStore } from "$lib/stores/tokenStore";
    import { get } from "svelte/store";

    export let data: ProjectDTO;

    async function removeCreator(username: string) {
        const res = await fetchHttp(`/project/${data.id}/creators/${username}`, {
            token: get(tokenStore),
            method: "delete"
        })
        if (!res.ok) {
            alertStore.update(a => {
                a.message = "Could not remove creator"
                a.color = "red"
                return a
            })
            return
        }
        data.creators = data.creators.filter(e => e.username !== username)
    }

    data.creators = [data.owner, ...data.creators]
</script>

<div class="creators row">
    {#each data.creators as creator}
        <div class="row {creator.username !== data.owner.username ? 'creator' : ''}">
            <a href="/{creator.username}">
                <Avatar
                    size="28px"
                    username={creator.username}
                    cursor="pointer"
                    margin="0"
                />
            </a>
            {#if creator.username !== ""}
                <button on:click={() => removeCreator(creator.username)}>delete</button>
            {/if}
        </div>
    {/each}
</div>

<style lang="scss">
    .creators {
        width: 100%;
        max-width: 720px;
        height: 48px;
        padding: 0 10px;
        margin: -10px 0 30px 0;
        box-sizing: border-box;
        overflow-x: auto;
        border: solid 1px var(--lightBorder);
        border-radius: 35px;
        
        button {
            display: none;
            border: none;
            background: transparent;
            color: var(--danger);
            cursor: pointer;
        }

        a {
            margin: 0 10px;
        }
        
        .creator {
            transition: 100ms ease-in-out;
            
            &:hover {
                border: solid 1px var(--lightBorder);
                border-radius: 20px;
                padding: 2.5px 0;
                padding-right: 10px;

                button {
                    display: block;

                    &:hover {
                        color: var(--dark-danger);
                    }
                }
            }
        }
    }
</style>
