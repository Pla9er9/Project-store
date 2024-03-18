<script lang="ts">
    import LoadingIndicator from "$components/LoadingIndicator.svelte";
    import fetchHttp from "$lib/fetchHttp";
    import { onMount } from "svelte";
    import { tokenStore } from "$lib/stores/tokenStore";
    import { get } from "svelte/store";
    import IssueRow from "$components/project/issues/IssueRow.svelte";
    import type { IssueDtoSimple } from "$lib/models/issue/IssueDtoSimple";

    let response: IssueDtoSimple[] | null = null;

    onMount(async () => {
        const res = await fetchHttp("/account/issues", {
            token: get(tokenStore),
        });
        if (res?.ok) {
            response = res.body;
        }
    });
</script>

<div id="issueList" class="column">
    <div class="row">
        <img src="/icons/issue_.svg" alt="" />
        <p>Your issues</p>
    </div>
    {#if !response}
        <div style="margin: 80px auto;">
            <LoadingIndicator size="35px" />
        </div>
    {:else if response.length === 0}
        <p
            style="margin: 45px auto;color: gray; font-family: 'Fira sans', sans-serif;font-size: 13px;"
        >
            No issue opened
        </p>
    {:else}
        {#each response as i}
            <IssueRow issue={i} />
        {/each}
    {/if}
</div>

<style lang="scss">
    #issueList {
        width: 92%;
        height: 320px;
        max-height: 700px;
        outline: solid 1px var(--lightBorder);
        border-radius: 5px;
        box-sizing: border-box;
        padding: 15px 25px;
        margin: 15px;
        align-items: start;

        * {
            margin: 5px 5px 14px;
        }

        img {
            margin-top: 8px;
            margin-right: 8px;
            width: 18px;
        }
    }
</style>
