<script lang="ts">
    import { goto } from "$app/navigation";
    import Input from "$components/forms/Input.svelte";
    import SubmitButton from "$components/forms/SubmitButton.svelte";
    import TextArea from "$components/forms/TextArea.svelte";
    import { PUBLIC_API_URL } from "$env/static/public";
    import { tokenStore } from "$lib/stores/tokenStore";
    import { createEventDispatcher } from "svelte";
    import { get } from "svelte/store";

    export let projectId: string;

    let title = "";
    let description = "";

    const dispatch = createEventDispatcher();

    let token = get(tokenStore)

    async function createNewIssue() {
        const body = JSON.stringify({
            title: title,
            description: description,
        });

        const req = await fetch(
            PUBLIC_API_URL + "/project/" + projectId + "/issues",
            {
                method: "POST",
                body: body,
                headers: {
                    "Content-Type": "application/json",
                    Authorization: "Bearer " + token,
                },
            },
        );
        if (!req.ok) {
            alert("Error with creating project");
            return;
        }
        await goto("");
    }
</script>

<div id="newIssueForm">
    <div class="headline">
        <img src="/icons/open_issue.svg" alt="" />
        <h1>New issue</h1>
    </div>
    <Input
        width="300px"
        bind:value={title}
        placeholder="Title"
        validator={(s) => (s.length > 3 && s.length < 50 ? "" : "Wrong length")}
    >
        <img src="/icons/t_letter.svg" alt="" />
    </Input>
    <TextArea
        width="55%"
        height="300px"
        maxlength={5000}
        bind:value={description}
        placeholder="Description"
    />
    <br />
    <SubmitButton
        callback={() => {
            createNewIssue();
            dispatch("submit");
        }}
        isValid={true}
        text="Create"
        iconUrl="/icons/checkmark.svg"
    />
    </div>

<style lang="scss">
    #newIssueForm {
        max-width: 500px;
        width: 95%;
        height: 640px;
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        margin: auto;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        background-color: #00000028;
        backdrop-filter: blur(15px);
        border: solid 1px var(--lightBorder);
        border-top: solid 1px #cbcaca45;
        border-radius: 10px;
        z-index: 99999;

        .headline {
            display: flex;
            align-items: center;
            width: 60%;
            margin-bottom: 30px;

            h1 {
                font-family: "Fira sans";
                color: rgb(207, 206, 206);
                margin-left: 15px;
            }
        }

        img {
            width: 24px;
        }
    }
</style>
