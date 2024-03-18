<script lang="ts">
    import fetchHttp from "$lib/fetchHttp";
    import { tokenStore } from "$lib/stores/tokenStore";
    import { get } from "svelte/store";

    export let applicationId: string

    let newGeneratedSecret: string | null = null;

    async function generateNewSecret() {
        const res = await fetchHttp(`/dev/application/${applicationId}/secret`, {
            method: "PUT",
            token: get(tokenStore)
        })
        newGeneratedSecret = res?.body
    }
</script>

<div class="panel">
    <img src="/icons/key_outline.svg" alt="" />
    <p class="label">New secret</p>
    {#if newGeneratedSecret}
        <p class="value">{newGeneratedSecret}</p>
    {:else}
        <button on:click={generateNewSecret}>Generate</button>
    {/if}
</div>

<style lang="scss">
    .panel {
        width: 100%;
        height: 70px;
        margin: 10px 15px;
        padding: 15px;
        box-sizing: border-box;
        border: solid 1px var(--lightBorder);
        border-radius: 5px;
        display: flex;
        align-items: center;
        overflow: auto hidden;

        .label {
            font-family: "Fira sans", sans-serif;
            margin-right: auto;
        }

        .value {
            max-width: 50%;
            width: max-content;
            max-height: 30px;
            overflow-x: auto;
            font-family: "Fira sans", sans-serif;
            font-size: 14px;
            white-space: nowrap;
            color: var(--mainColor);
        }

        p {
            width: max-content;
        }

        img {
            width: 22px;
            margin-right: 10px;
        }

        button {
            width: 100px;
            height: 32px;
            margin-right: 5px;
            border: solid 2px var(--mainColor);
            border-radius: 5px;
            color: #fff;
            background-color: var(--background);
            cursor: pointer;
            transition:
                background 250ms ease-in-out,
                color 250ms ease-in-out;

            &:hover {
                background-color: var(--mainColor);
                color: var(--background);
            }
        }
    }
</style>
