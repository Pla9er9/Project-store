<script lang="ts">
    export let data

    import Filters from "$components/search/Filters.svelte"
    import Result from "$components/search/Result.svelte"

    let type: "mixed" | "user" | "project" = "mixed"
    if (data.type === "users") {
        type = "user"
    } else if (data.type?.startsWith("project-")) {
        type = "project"
    }

</script>

<main class="column">
    <Filters query={data.query} type={data.type} />
    {#if data.res.length === 0}
        <img src="/icons/question.svg" alt="">
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