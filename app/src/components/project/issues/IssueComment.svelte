<script lang="ts">
    import Avatar from "$components/Avatar.svelte";
    import type { IssueCommentDto } from "$lib/models/issue/IssueCommentDto";

    export let comment: IssueCommentDto;
</script>

<div class="line" />
<div class="line" style="transform: rotateX(180deg);" />
<div class="issueComment">
    <div style="width: 100%;display:flex;align-items:center">
        <a href="/{comment.createdBy.username}">
            <Avatar
                size="40px"
                margin="10px 15px 10px 0"
                cursor="pointer"
                username={comment.createdBy.username}
            />
        </a>
        <p>
            {comment.createdBy.username} <br /><small
                >{comment.created.slice(0, 10)}</small
            >
        </p>
    </div>
    <p style="margin-left: 10px;font-family: sans-serif;">{comment.comment}</p>
</div>

<style lang="scss">
    .line {
        max-width: 1px;
        height: 50px;
        margin-left: 20px;
        background: linear-gradient(
            0deg,
            var(--background) 0%,
            rgb(169 215 218) 0%,
            var(--background) 100%
        );
    }

    .issueComment {
        max-width: 1000px;
        width: 90%;
        padding: 5px 15px;
        margin: 30px 0;
        outline: solid 1px rgba(105 105 105 / 18.5%);
        border-radius: 10px;
        display: flex;
        flex-wrap: wrap;
        align-items: center;

        small {
            color: rgb(168 168 168);
        }
    }
</style>
