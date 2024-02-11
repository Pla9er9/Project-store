<script lang="ts">
    import { quintOut } from "svelte/easing";
    import { fly } from "svelte/transition";

    export let username: string | null;
</script>

<div
    class="mobileLinks column"
    transition:fly={{
        delay: 50,
        duration: 200,
        x: 0,
        y: -400,
        opacity: 0.5,
        easing: quintOut,
    }}
>
    {#if username}
        <a href="/{username}">{username}</a>
    {:else}
        <a href="/login">Sign in</a>
        <a href="/register">Sign up</a>
    {/if}
    <a href="/explore">Explore</a>
    {#if username}
        <a href="/new">New</a>
        <a href="/settings">Settings</a>
        <a href="/console">Console</a>
        {/if}
    <a href="/report">Report error</a>
    <a href="/status">Server status</a>
</div>

<style lang="scss">
    .mobileLinks {
        width: 100vw;
        height: 100vh;
        z-index: 9999999;
        padding: 20px 0;
        box-sizing: border-box;
        position: absolute;
        background-color: var(--background);
        align-items: start;

        a {
            width: 100%;
            margin: 15px 0 15px 0;
            text-indent: 45px;
            color: rgb(146, 146, 146);
            font-size: 16px;
            font-family: "Fira sans";

            &:hover {
                color: #fff;
            }
        }
    }
</style>
