<script lang="ts">
    import LoadingIndicator from "$components/LoadingIndicator.svelte";
    import fetchHttp from "$lib/fetchHttp";
    import { onMount } from "svelte";
    import { tokenStore } from "$lib/stores/tokenStore";
    import { get } from "svelte/store";
    import Result from "$components/search/Result.svelte";
    import Button from "$components/Button.svelte";
    import { goto } from "$app/navigation";

    let response: any = null;

    onMount(async () => {
        const res = await fetchHttp("/account/projects", {
            token: get(tokenStore),
        });
        if (res?.ok) {
            response = res.body;
        }
    });
</script>

<div id="lastProjects" class="column">
    <div class="row">
        <img src="/icons/project_green_.svg" alt="" />
        <p>Last projects</p>
    </div>
    {#if !response}
        <div style="margin: 80px auto;">
            <LoadingIndicator size="35px" />
        </div>
    {:else if response.length === 0}
        <div class="column" style="margin: 45px auto;">
            <p
                style="color: gray; font-family: 'Fira sans';font-size: 13px; margin-bottom: 25px;"
            >
                You dont have any projects
            </p>
            <Button
                text="Create first"
                onClick={() => goto("/new")}
                outline={true}
            />
        </div>
    {:else}
        <div
            class="column"
            style="width: 98%; overflow-y: auto;padding-right: 12px;"
        >
            {#each response as p}
                <div style="margin: 5px 0; width: 100%">
                    <Result data={p} type="project" />
                </div>
            {/each}
        </div>
    {/if}
</div>

<style lang="scss">
    #lastProjects {
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
            margin: 5px 5px 14px 5px;
        }

        img {
            margin-top: 8px;
            margin-right: 8px;
            width: 20px;
        }
    }
</style>
