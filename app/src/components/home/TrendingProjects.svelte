<script lang="ts">
    import LoadingIndicator from "$components/LoadingIndicator.svelte";
    import Result from "$components/search/Result.svelte";
    import fetchHttp from "$lib/fetchHttp";
    import { onMount } from "svelte";

    let response: any = null;

    onMount(async () => {
        const res = await fetchHttp("/project/mostLiked", {});
        if (res?.ok) {
            response = res.body;
        }
    });
</script>

<div id="treningProjects" class="column">
    <div class="row" style="width: 100%;">
        <img src="/icons/explore.svg" alt="" />
        <p style="margin-right: auto;">Trending projects</p>
        <a href="/explore">Explore more</a>
    </div>
    {#if !response}
        <div style="margin: 80px auto;">
            <LoadingIndicator size="35px" />
        </div>
    {:else}
        {#each response.content as p}
            <div style="margin: 6px 0; width: 100%;">
                <Result data={p} type="project" wide={false} />
            </div>
        {/each}
    {/if}
</div>

<style lang="scss">
    #treningProjects {
        width: 100%;
        height: 100%;
        max-height: 800px;
        outline: solid 1px var(--lightBorder);
        border-radius: 5px;
        padding: 15px 25px;
        align-items: start;
        box-sizing: border-box;

        * {
            margin: 5px 5px 14px;
        }

        img {
            margin-top: 6px;
            width: 18px;
        }

        a {
            color: var(--mainColor);

            &:hover {
                text-decoration: underline;
            }
        }
    }
</style>
