<script lang="ts">
	export let projects: any[];

	import Result from '$components/search/Result.svelte';
</script>

<div id="projects">
	{#each projects as project}
		<div style="max-width: 400px;width:95%;margin: 8px 0;">
			<Result data={project} type="project" wide={false} />
		</div>
	{/each}
</div>

<style lang="scss">
	#projects {
		max-width: 880px;
		padding-right: 10px;
		width: 95%;
		height: 342px;
		overflow-y: auto;
		overflow-x: hidden;
		display: flex;
		flex-wrap: wrap;
		justify-content: space-around;
	}
</style>
