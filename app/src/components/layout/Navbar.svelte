<script lang="ts">
    import { beforeNavigate } from "$app/navigation";
    import Avatar from "../Avatar.svelte";
    import LinkButton from "./LinkButton.svelte";
    import MobileNavigation from "./MobileExpandNavigation.svelte";
    import SearchInput from "./SearchInput.svelte";

    export let username: string | null;
    let home = "/";
    if (username !== null) {
        home = "/home";
    }

    let navigationOpen = false;

    beforeNavigate(() => {
        if (navigationOpen) {
            changeNavVisibility();
        }
    });

    function changeNavVisibility() {
        navigationOpen = !navigationOpen;
        const body = document.querySelector("body");
        if (body !== null) {
            if (navigationOpen) {
                body.style.overflowY = "hidden";
            } else {
                body.style.overflowY = "auto";
            }
        }
    }
</script>

<nav class="row">
    <a href={home} class="row home">
        <img src="/icons/project.svg" alt="" />
        <h1>Project store</h1>
    </a>
    <SearchInput />
    {#if username === null}
        <div id="authBar">
            <LinkButton text="Sign in" link="/login" />
            <div class="line" />
            <LinkButton text="Sign up" link="/register" />
        </div>
    {:else}
        <a class="iconBtn" href="/new">
            <img src="/icons/plus.svg" alt="" />
        </a>
        <a class="iconBtn" href="/explore">
            <img src="/icons/explore.svg" alt="" />
        </a>
        <a class="iconBtn" href="/settings">
            <img src="/icons/settings.svg" alt="" />
        </a>
        <a href="/{username}" style="margin: 0 20px;" id="avatarLink">
            <Avatar size="40px" cursor="pointer" margin="0" {username} />
        </a>
    {/if}
    <button on:click={changeNavVisibility}>
        <img src="/icons/{navigationOpen ? 'cross_white' : 'menu'}.svg" alt="" />
    </button>
</nav>
{#if navigationOpen}
    <MobileNavigation {username} />
{/if}

<style lang="scss">
    nav {
        width: 100%;
        height: 50px;
        padding: 10px 0;
        background-color: var(--background);
        border-bottom: 1px solid #202020;
        z-index: 99999999;

        .home {
            margin-right: auto;

            img {
                width: 22px;
                margin: 0 12px 0 25px;
            }

            h1 {
                margin: 0;
                margin-bottom: 4px;
                font-size: 20px;
                color: #fff;
                font-family: Syne, sans-serif;
            }
        }

        .iconBtn {
            width: 45px;
            height: 45px;
            display: flex;
            justify-content: center;
            align-items: center;
            background-color: inherit;
            border: none;
            margin: 0 10px;
            cursor: pointer;

            &:hover {
                border-radius: 50%;
                background-color: rgba(128 128 128 / 13%);
            }

            img {
                width: 21px;
            }
        }

        button {
            display: none;
            background-color: var(--background);
            border: none;
            margin-right: 10px;
        }

        #authBar {
            display: flex;
            border: solid 1px var(--lightBorder);
            height: max-content;
            border-radius: 9px;
            margin-left: auto;
            margin-right: 25px;

            .line {
                width: 1px;
                height: 30px;
                background: var(--lightBorder);
            }
        }
    }

    @media only screen and (width <= 600px) {
        .iconBtn,
        #avatarLink,
        #authBar {
            display: none !important;
        }

        button {
            display: block !important;
        }
    }
</style>
