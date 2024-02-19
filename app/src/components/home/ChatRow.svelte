<script lang="ts">
    export let username: string;

    import Avatar from "$components/Avatar.svelte";
</script>

<div class="chatRow row">
    <Avatar {username} size="32px" />
    <a href={username} class="userProfileLink">{username}</a>
    <a href="/chat/{username}" class="chatLink">
        <img src="/icons/letter.svg" alt="" />
    </a>
</div>

<style lang="scss">
    .chatRow {
        width: 100%;
        height: 55px;
        margin: 8px 0;
        outline: solid 1px var(--lightBorder);
        border-radius: 25px;
        box-sizing: border-box;
        padding: 0 20px;
        padding-right: 10px;

        .userProfileLink {
            margin: 0 auto 0 15px;
            color: rgb(184, 184, 184);
            font-family: "Fira sans";
            font-size: 13px;
            transition: color 150ms ease-in-out;

            &:hover {
                color: var(--mainColor);
            }
        }

        .chatLink {
            width: 42px;
            height: 42px;
            display: flex;
            border-radius: 100%;
            transition: background-color 150ms ease-in-out;

            &:hover {
                background-color: #ffffff15;
            }

            img {
                margin: auto;
                width: 18px;
            }
        }
    }
</style>
