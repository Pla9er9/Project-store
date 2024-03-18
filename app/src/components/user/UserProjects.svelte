<script lang="ts">
	export let userId: string;

	import LoadingIndicator from "$components/LoadingIndicator.svelte"
	import Result from '$components/search/Result.svelte';
    import fetchHttp from '$lib/fetchHttp';
    import { alertStore } from '$lib/stores/alertStore';
    import { tokenStore } from "$lib/stores/tokenStore";
    import { get } from "svelte/store";

	let projects: any[] = []

	async function load() {
		const res = await fetchHttp(`/project?userId=${userId}`, {
			token: get(tokenStore)
		})
		if (!res.ok) {
			alertStore.update(a => {
				a.message = "Could not load user projects"
				a.color = "red"
				return a
			})
		}
		projects = res.body
	}
</script>

<div id="projects">
	{#await load()}
		<LoadingIndicator size="40px" />
	{:then _} 
		{#each projects as project}
			<div class="resultWrapper">
				<Result data={project} type="project" wide={false} />
			</div>
		{/each}
	{/await}
</div>

<style lang="scss">
	#projects {
		max-width: 880px;
		padding-right: 10px;
		width: 95%;
		height: max-content;
		overflow-y: auto;
		overflow-x: hidden;
		display: flex;
		flex-wrap: wrap;
		justify-content: start;

		.resultWrapper {
			width:95%;
			max-width: 400px;
			margin: 8px 12px;
		}
	}
</style>
