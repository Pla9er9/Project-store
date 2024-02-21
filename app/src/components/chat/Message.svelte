<script lang="ts">
    import Avatar from "$components/Avatar.svelte";

    export let message: any;
    export let username;

    let isMessageYours = message.senderUsername === username;
    let hover = false;
</script>

<div class="column">
    <div
        class="row"
        style={isMessageYours ? "margin-left: auto;" : "margin-rightL auto;"}
    >
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
        <!-- svelte-ignore a11y-no-static-element-interactions -->
        <div
            class="message column {isMessageYours
                ? 'yourMessage'
                : 'notYourMessage'}"
            on:mouseenter={() => (hover = true)}
            on:mouseleave={() => (hover = false)}
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
    {#if hover}
        <p
            class="sendTime"
            style={isMessageYours
                ? "margin-left: auto;"
                : "margin-rightL auto;"}
        >
            {message.sendDateTime.slice(11, 16)} - {message.sendDateTime.slice(
                0,
                10
            )}
        </p>
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

    .sendTime {
        margin: 0 30px;
        font-size: 11px;
        color: gray;
    }

    .yourMessage {
        margin-left: auto;
        background-color: rgb(0, 110, 255);
    }

    .notYourMessage {
        margin-right: auto;
        border: solid 1px var(--lightBorder);
    }
</style>
