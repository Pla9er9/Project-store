<script lang="ts">
	export let projects: any[];
	export let username: string;

	import ProjectRow from '$components/user/ProjectRow.svelte';
</script>

<div id="projects">
	{#each projects as project}
		<div style="max-width: 400px;width:95%;margin: 12px;">
			<ProjectRow {project} {username} />
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
