<script lang="ts">
    export let data;

    import Filters from "$components/search/Filters.svelte";
    import Result from "$components/search/Result.svelte";
    import LoadingIndicator from "$components/LoadingIndicator.svelte";
    import fetchHttp from "$lib/fetchHttp.js";

    let loading = false;

    let type: "user" | "project" = "project";
    if (data.type === "users") {
        type = "user";
    } else if (data.type?.startsWith("project-")) {
        type = "project";
    }

    async function loadNewData(newType: string) {
        let endpoint = "";
        loading = true;
        data.res = [];
        if (newType === "users") {
            type = "user";
            endpoint = "users/";
        } else if (newType === "project name") {
            type = "project";
            endpoint = "projects/";
        } else if (newType === "project tag") {
            type = "project"
            endpoint = "tags/";
        }

        const res = await fetchHttp(`/search/${endpoint}${data.query}`, {});
        data.res = res?.body;
        loading = false;
    }
</script>

<main class="column">
    <Filters query={data.query} type={data.type} onSelect={loadNewData} />
    {#if loading}
        <div style="margin-top: 250px"></div>
        <LoadingIndicator />
    {:else if data.res.length === 0}
        <img src="/icons/question.svg" alt="" />
        <p>0 results found</p>
    {:else}
        {#each data.res as res}
            <Result data={res} {type} />
        {/each}
    {/if}
</main>

<style lang="scss">
    main {
        width: 100%;
        max-width: 1200px;
        margin: 50px auto;

        img {
            margin-top: 80px;
            width: 150px;
        }

        p {
            color: rgb(175, 174, 174);
        }
    }
</style>
