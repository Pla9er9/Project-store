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
	<p>Files</p>
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
		padding: 20px 15px;
		box-sizing: border-box;
		border-right: solid 1px rgb(77, 77, 77);
		display: flex;
		flex-direction: column;
		align-items: center;
		overflow: auto;

		p {
			color: #fff;
			align-self: flex-start;
			margin: 0 12px 18px 14px;
		}
	}
</style>
