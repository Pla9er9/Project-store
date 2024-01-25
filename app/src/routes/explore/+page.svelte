<script lang="ts">
    export let data

	import LoadingIndicator from '$components/LoadingIndicator.svelte';
    import ProjectRow from '$components/user/ProjectRow.svelte';
	import fetchHttp from '$lib/fetchHttp.js';
	import type { ProjectDtoSimple } from '$lib/models/project/ProjectDtoSimple.js';

    let apiPage = 1
    let loading = false
    let showBy = "trending"

    async function getMore() {
        loading = true
        const res = await fetchHttp(`/project/${showBy}?page=${apiPage}`, {})
        projects = projects.concat(res?.body.content)
        loading = false
        apiPage += 1
    }

    async function change() {
        apiPage = 0
        projects = []
        await getMore()
    }

    let projects: ProjectDtoSimple[] = data.content
</script>

<main>
	<h1><span style="text-transform: capitalize;">{showBy}</span> projects</h1>
	<div id="trending">
		<div id="filters">
            <p style="margin-right: auto;">Filters</p>
            <div style="display: flex;align-items:center;">
                <p style="margin-right: 18px;">Show</p>
                <select bind:value={showBy} on:change={change}>
                    <option value="trending">Trending</option>
                    <option value="mostLiked">Most likes</option>
                </select>
                <p style="margin-right: 14px;">Language</p>
                <select>
                    <option value="any">Any</option>
                    <option value="Javascript">Javascript</option>
                    <option value="TypeScript">TypeScript</option>
                    <option value="Python">Python</option>
                    <option value="Go">Go</option>
                    <option value="Java">Java</option>
                    <option value="Kotlin">Kotlin</option>
                    <option value="PHP">PHP</option>
                    <option value="C">C</option>
                    <option value="C++">C++</option>
                    <option value="C#">C#</option>
                    <option value="Swift">Swift</option>
                    <option value="R">R</option>
                    <option value="Ruby">Ruby</option>
                    <option value="Rust">Rust</option>
                    <option value="Scala">Scala</option>
                    <option value="SQL">SQL</option>
                    <option value="HTML">HTML</option>
                    <option value="CSS">CSS</option>
                    <option value="Perl">Perl</option>
                </select>
            </div>
        </div>
        {#each projects as project}
            <div style="margin: 15px 0; width:95%">
                <ProjectRow {project} username={""} />
            </div>    
        {/each}
        {#if apiPage !== data.totalPages}
            <button on:click={getMore}>Load more</button>
        {/if}
        {#if loading}
            <div style="margin-top: 40px;">
                <LoadingIndicator />
            </div>    
        {/if}
	</div>
</main>

<style lang="scss">
	main {
		width: 95%;
		max-width: 1000px;
		margin: 0 auto;
		margin-top: 70px;
        margin-bottom: 60px;

        h1 {
            font-family: 'Inter', sans-serif;
            margin-left: 20px;
            margin-bottom: 30px;
        }

		#trending {
            width: 100%;
            height: max-content;
			border: solid 1px #202020;
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
                font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
                font-weight: 500;
                background: rgb(55, 0, 255);
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }

            select {
                background-color: #111010;
                outline: none;
                border: none;
                color: rgb(209, 209, 209);
                border-bottom: solid 1px #9b80fd;
                border-bottom: 1px solid transparent;
                border-image: linear-gradient(0.25turn, #cb80fd, rgb(111, 0, 255));
                border-image-slice: 1;
                margin-top: 6px;
                padding-bottom: 4px;
                margin-right: 30px;

                &:first-of-type {
                    border-image: linear-gradient(0.25turn, #f7df75, rgb(250, 151, 70));
                    border-image-slice: 1;
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
				background-color: #111010;
                margin-bottom: 30px;

                p {
                    font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
                    color: rgb(114, 114, 114);

                    &:first-of-type {
                        margin-left: 20px;
                    }
                }
			}
		}
	}
</style>
