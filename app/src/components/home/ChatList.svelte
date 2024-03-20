<script lang="ts">
    import LoadingIndicator from "$components/LoadingIndicator.svelte";
    import fetchHttp from "$lib/fetchHttp";
    import { onMount } from "svelte";
    import ChatRow from "./ChatRow.svelte";
    import { tokenStore } from "$lib/stores/tokenStore";
    import { get } from "svelte/store";

    export let chatList: string[] | null = null;

    onMount(async () => {
        const res = await fetchHttp("/account/chats", {
            token: get(tokenStore),
        });
        if (res?.ok) {
            chatList = res.body;
        }
    });
</script>

<div id="chatList" class="column">
    <div class="row">
        <img src="/icons/message.svg" alt="" />
        <p>Your chats</p>
    </div>
    {#if !chatList}
        <div style="margin: 80px auto;">
            <LoadingIndicator size="35px" />
        </div>
    {:else if chatList.length === 0}
        <p
            style="margin: 45px auto;color: gray; font-family: 'Fira sans', sans-serif;font-size: 13px;"
        >
            0 chats open
        </p>
    {:else}
        {#each chatList as p}
            <ChatRow username={p} />
        {/each}
    {/if}
</div>

<style lang="scss">
    #chatList {
        width: 100%;
        height: 100%;
        max-height: 800px;
        outline: solid 1px var(--lightBorder);
        border-radius: 5px;
        padding: 15px 25px;
        align-items: start;
        overflow-y: auto;
        box-sizing: border-box;

        * {
            margin: 5px 5px 14px;
        }

        img {
            margin-top: 8px;
            width: 18px;
        }
    }
</style>
