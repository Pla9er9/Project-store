<script lang="ts">
    export let data;

    import LoadingIndicator from "$components/LoadingIndicator.svelte";
    import Result from "$components/search/Result.svelte";
    import Select from "$components/forms/Select.svelte";
    import fetchHttp from "$lib/fetchHttp.js";
    import type { ProjectDtoSimple } from "$lib/models/project/ProjectDtoSimple.js";
    import { onMount } from "svelte";
    import Observer from "$components/Observer.svelte";

    let apiPage = 0;
    let loading = false;
    let showBy = "Trending";
    let language = "any";
    let apiVariablePath = "trending"
    let isWide = true;

    async function getMore() {
        loading = true;
        let lang = ""
        if (language != "any") {
            lang = "&language=" + language
        }
        const res = await fetchHttp(`/project/${apiVariablePath}?page=${apiPage}${lang}`, {});
        projects = projects.concat(res?.body.content);
        loading = false;
        apiPage += 1;
    }

    async function change() {
        switch (showBy) {
            case "Trending":
                apiVariablePath = "trending"
                break
            case "Most likes":
                apiVariablePath = "mostLiked"
                break
        }
        apiPage = 0;
        projects = [];
        await getMore();
    }

    onMount(() => {
        if (window.innerWidth <= 540) {
            isWide = false;
        }
    });

    let projects: ProjectDtoSimple[] = data.data?.body.content;
</script>

<svelte:head>
    <title>Explore</title>
</svelte:head>

<main>
    <div id="trending">
        <div id="filters">
            <p style="margin-right: auto;color: #fff;">Explore projects</p>
            <div style="flex-wrap: wrap;justify-content: center" class="row">
                <div style="margin: 0 10px;">
                    <Select
                        text="Show"
                        bind:value={showBy}
                        onSelect={change}
                        imgSrc="/icons/public.svg"
                        options={["Trending", "Most likes"]}
                    />
                </div>
                <div style="margin: 0 10px;">
                    <Select
                        text="Language"
                        bind:value={language}
                        onSelect={change}
                        imgSrc="/icons/license.svg"
                        options={[
                            "any",
                            "js",
                            "ts",
                            "py",
                            "go",
                            "java",
                            "kt",
                            "php",
                            "c",
                            "cpp",
                            "cs",
                            "swift",
                            "r",
                            "ruby",
                            "rs",
                            "scala",
                            "sql",
                            "html",
                            "css",
                            "pl",
                        ]}
                    />
                </div>
            </div>
        </div>
        {#if projects !== undefined && projects.length !== 0}
            {#each projects as project}
                <Result data={project} type="project" wide={isWide} />
            {/each}
            {#if apiPage !== data.data?.body.totalPages - 1}
                <Observer onvisible={getMore} />
            {/if}
            {#if loading}
                <div style="margin-top: 40px;">
                    <LoadingIndicator />
                </div>
            {/if}
        {/if}
    </div>
</main>

<style lang="scss">
    main {
        width: 95%;
        max-width: 1000px;
        margin: 0 auto;
        margin-top: 60px;
        margin-bottom: 60px;

        #trending {
            width: 100%;
            height: max-content;
            border-radius: 8px;
            display: flex;
            flex-direction: column;
            align-items: center;
            padding-bottom: 30px;

            #filters {
                width: 100%;
                min-height: 60px;
                height: max-content;
                display: flex;
                flex-wrap: wrap;
                align-items: center;
                border-top-right-radius: 8px;
                border-top-left-radius: 8px;
                background-color: var(--background);
                border: solid 1px var(--lightBorder);

                p {
                    font-family: sans-serif;
                    color: rgb(158, 157, 157);

                    &:first-of-type {
                        margin-left: 20px;
                    }
                }
            }
        }
    }
</style>
