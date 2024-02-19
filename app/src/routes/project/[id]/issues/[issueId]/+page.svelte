<script lang="ts">
    import Avatar from "$components/Avatar.svelte";
    import CommentInput from "$components/project/issues/CommentInput.svelte";
    import IssueComment from "$components/project/issues/IssueComment.svelte";
    import fetchHttp from "$lib/fetchHttp.js";
    import type { IssueDto } from "$lib/models/issue/IssueDto.js";

    export let data;
    async function closeIssue() {
        issue.open = false;
        await fetchHttp(`/project/${data.slug}/issues/${data.issueId}/close`, {
            method: "post",
            token: data.token,
        });
    }

    const issue: IssueDto = data.data;
    let messageValue = "";

    async function newComment() {
        const res = await fetchHttp(`/issue/${data.issueId}/comment`, {
            method: "post",
            body: JSON.stringify({
                comment: messageValue,
            }),
            token: data.token,
        });
        issue.comments = [...issue.comments, res?.body];
        messageValue = "";
    }
</script>

<main>
    <div class="row" style="margin-bottom: 10px;">
        <h1>{issue.title}</h1>
        {#if issue.open}
            <button id="closeIssueBtn" on:click={closeIssue}>Close issue</button
            >
        {/if}
    </div>
    <div class="stats row">
        <Avatar
            margin="0 12px 0 0"
            username={issue.createdBy.username}
            size="22px"
        />
        <a href="/{issue.createdBy.username}">{issue.createdBy.username}</a>
        <div class="circle {issue.open ? 'open' : 'closed'}" />
        <p>{issue.open ? "Open" : "Closed"}</p>
        <svg
            style="margin: 0 10px 0 15px;"
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 24 24"
            ><path
                fill="var(--mainColor)"
                d="M12 14q-.425 0-.713-.288T11 13q0-.425.288-.713T12 12q.425 0 .713.288T13 13q0 .425-.288.713T12 14Zm-4 0q-.425 0-.713-.288T7 13q0-.425.288-.713T8 12q.425 0 .713.288T9 13q0 .425-.288.713T8 14Zm8 0q-.425 0-.713-.288T15 13q0-.425.288-.713T16 12q.425 0 .713.288T17 13q0 .425-.288.713T16 14Zm-4 4q-.425 0-.713-.288T11 17q0-.425.288-.713T12 16q.425 0 .713.288T13 17q0 .425-.288.713T12 18Zm-4 0q-.425 0-.713-.288T7 17q0-.425.288-.713T8 16q.425 0 .713.288T9 17q0 .425-.288.713T8 18Zm8 0q-.425 0-.713-.288T15 17q0-.425.288-.713T16 16q.425 0 .713.288T17 17q0 .425-.288.713T16 18ZM5 22q-.825 0-1.413-.588T3 20V6q0-.825.588-1.413T5 4h1V2h2v2h8V2h2v2h1q.825 0 1.413.588T21 6v14q0 .825-.588 1.413T19 22H5Zm0-2h14V10H5v10Z"
            /></svg
        >
        <p>{issue.created.slice(0, 10)}</p>
    </div>
    <p style="margin-bottom: 40px;font-family: sans-serif">
        {issue.description}
    </p>
    {#each issue.comments as comment}
        <IssueComment {comment} />
    {/each}
    <CommentInput bind:value={messageValue} sendMessage={newComment} />
</main>

<style lang="scss">
    main {
        max-width: 900px;
        width: 90%;
        margin: 0 auto;
        margin-top: 80px;

        #closeIssueBtn {
            width: 100px;
            height: 35px;
            color: #fff;
            margin-left: auto;
            border: solid 1px rgb(114, 6, 255);
            background-color: rgba(62, 2, 141, 0.466);
            border-radius: 15px;
            cursor: pointer;
        }

        a {
            color: #fff;
            font-family: "Fira sans";
            transition: 150ms ease-in-out;
            font-size: 15px;
            margin-right: 15px;

            &:hover {
                color: var(--mainColor);
            }
        }

        h1 {
            margin: 0;
            margin-top: 20px;
            width: 60%;
            max-width: 500px;
            word-break: break-all;
        }

        .stats {
            svg {
                width: 22px;
                height: 22px;
            }

            .circle {
                width: 15px;
                height: 15px;
                margin: 0 15px 0 2px;
                border-radius: 50%;
            }

            .open {
                background: rgb(136, 252, 3);
                background: radial-gradient(
                    circle,
                    rgba(136, 252, 3, 1) 0%,
                    rgba(39, 126, 0, 1) 100%
                );
            }

            .closed {
                background: rgb(164, 0, 251);
                background: radial-gradient(
                    circle,
                    rgba(164, 0, 251, 1) 0%,
                    rgba(55, 0, 238, 1) 100%
                );
            }
        }
    }
</style>
