<script lang="ts">
    export let data;

    import LoadingIndicator from "$components/LoadingIndicator.svelte";
    import Result from "$components/search/Result.svelte";
    import Select from "$components/forms/Select.svelte";
    import fetchHttp from "$lib/fetchHttp.js";
    import type { ProjectDtoSimple } from "$lib/models/project/ProjectDtoSimple.js";
    import { onMount } from "svelte";

    let apiPage = 1;
    let loading = false;
    let showBy = "trending";
    let isWide = true

    async function getMore() {
        loading = true;
        const res = await fetchHttp(`/project/${showBy}?page=${apiPage}`, {});
        projects = projects.concat(res?.body.content);
        loading = false;
        apiPage += 1;
    }

    async function change() {
        apiPage = 1;
        projects = [];
        await getMore();
    }

    onMount(() => {
        if (window.screenX <= 540) {
            isWide = false
        }
    })

    let projects: ProjectDtoSimple[] = data.data?.body.content;
</script>

<svelte:head>
    <title>Explore</title>
</svelte:head>

<main>
    <div id="trending">
        <div id="filters">
            <p style="margin-right: auto;">Explore projects</p>
            <div style="flex-wrap: wrap;justify-content: center" class="row">
                <div style="margin: 0 10px;">
                    <Select
                        text="Show"
                        bind:value={showBy}
                        imgSrc="/icons/public.svg"
                        options={["Trending", "Most likes"]}
                    />
                </div>
                <div style="margin: 0 10px;">
                    <Select
                        text="Language"
                        bind:value={showBy}
                        imgSrc="/icons/license.svg"
                        options={[
                            "any",
                            "Javascript",
                            "TypeScript",
                            "Python",
                            "Go",
                            "Java",
                            "Kotlin",
                            "PHP",
                            "C",
                            "C++",
                            "C#",
                            "Swift",
                            "R",
                            "Ruby",
                            "Rust",
                            "Scala",
                            "SQL",
                            "HTML",
                            "CSS",
                            "Perl",
                        ]}
                    />
                </div>
            </div>
        </div>
        {#if projects !== undefined && projects.length !== 0}
            {#each projects as project}
                <Result data={project} type="project" wide={isWide} />
            {/each}
            {#if apiPage !== data.data?.body.totalPages}
                <button on:click={getMore}>Load more</button>
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

            button {
                width: 120px;
                height: 35px;
                margin-top: 20px;
                color: #fff;
                font-family: sans-serif;
                font-weight: 500;
                background: linear-gradient(
                    180deg,
                    rgb(0, 153, 255),
                    #4f84ffa7
                );
                border: none;
                border-radius: 5px;
                cursor: pointer;

                &:hover {
                    background: #4c7df1a7;
                }
            }

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
