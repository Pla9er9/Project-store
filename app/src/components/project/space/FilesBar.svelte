<script lang="ts">
	export let directories: string[];
	export let files: string[];
	
	import { onMount } from 'svelte';
	import Directory from './Directory.svelte';
	import File from './File.svelte';
	import RightClickMenu from './RightClickMenu.svelte';

	let menuVisible = false;
	let mouseX = 0;
	let mouseY = 0;

	function showMenu(e: MouseEvent) {
		e.preventDefault();
		mouseX = e.pageX;
		mouseY = e.pageY;
		menuVisible = true;
	}

	onMount(() => {
		document.onclick = () => (menuVisible = false);
	});
</script>

<!-- svelte-ignore a11y-no-static-element-interactions -->
<div id="filesBar" on:contextmenu={showMenu}>
	<p>Projectname</p>
	<RightClickMenu bind:mouseX bind:mouseY bind:menuVisible />
	{#each directories as dir}
		<Directory path={dir} />
	{/each}
	{#each files as file}
		<File path={file} />
	{/each}
</div>

<style lang="scss">
	#filesBar {
		width: 400px;
		height: 100vh;
		border-right: solid 1px rgb(77, 77, 77);
		display: flex;
		flex-direction: column;
		align-items: center;
		overflow: auto;

		p {
			width: 100%;
			text-indent: 30px;
			color: var(--mainColor);
			text-align: start;
			margin: 18px 0;
		}
	}
</style>
