<script lang="ts">
    import Avatar from "$components/Avatar.svelte";

    export let message: any;
    export let username;

    let isMessageYours = message.senderUsername === username;
</script>

<div style="display: flex;align-items:center">
    {#if !isMessageYours}
        <a href="/{message.recipientUsername}">
            <Avatar
                cursor="pointer"
                margin="0 0 0 20px"
                username={message.recipientUsername}
                size="35px"
            />
        </a>
    {/if}
    <div
        class="message row {isMessageYours ? 'yourMessage' : 'notYourMessage'}"
    >
        <p>{message.content}</p>
    </div>
    {#if isMessageYours}
        <Avatar
            margin="0 30px 0 0"
            username={message.recipientUsername}
            size="35px"
        />
    {/if}
</div>

<style lang="scss">
    .message {
        width: max-content;
        height: max-content;
        max-width: 65%;
        word-break: break-all;
        padding: 10px 15px;
        margin: 15px;
        border-radius: 15px;

        p {
            font-family: "Fira sans";
            margin: 0;
            color: #e9e9e9;
        }
    }

    .yourMessage {
        margin-left: auto;
        border: solid 1px #fff;
    }

    .notYourMessage {
        margin-right: auto;
        border: solid 1px var(--lightBorder);
    }
</style>
