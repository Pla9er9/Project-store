<script lang="ts">
	export let userId: string;

	import LoadingIndicator from "$components/LoadingIndicator.svelte"
	import Result from '$components/search/Result.svelte';
    import fetchHttp from '$lib/fetchHttp';
    import { alertStore } from '$lib/stores/alertStore';

	let projects: any[] = []

	async function load() {
		const res = await fetchHttp(`/project?userId=${userId}`, {})
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
			<div style="max-width: 400px;width:95%;margin: 8px 0;">
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
		justify-content: space-around;
	}
</style>
