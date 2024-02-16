<script lang="ts">
	import { PUBLIC_API_URL } from '$env/static/public';
	import File from './File.svelte';
	import { spaceStore } from '$lib/stores/spaceStore';
	import { get } from 'svelte/store';

	export let path: string;

	let expand = false;
	let loaded = false;

	let directories: string[] = [];
	let files: string[] = [];

	async function expandFolder() {
		expand = !expand;
		if (!expand) return;
		await loadDirectory();
		loaded = true;
	}

	function getNameByPath(path: string): string {
		let res = '';
		for (let i = path.length - 1; i >= 0; i--) {
			if (path[i] === '/') {
				return res;
			} else {
				res = path[i] + res;
			}
		}
		return res;
	}

	async function loadDirectory() {
		if (loaded) {
			return;
		}
		const res = await fetch(
			`${PUBLIC_API_URL}/project/${get(spaceStore).projectId}/files?path=${path}`
		);
		const body = await res.json();
		if (body.folders !== undefined) {
			directories = [...body.folders];
			files = [...body.files];
		}

		loaded = true;
	}

	let count = 0;
	for (let i = 0; i < path.length; i++) {
		if (path[i] === '/') {
			count++;
		}
	}
</script>

<div>
	<div style="display: flex;align-items:center;--marginL: {20 + count * 12}px">
		{#if expand}
			<img src="icons/dir_expand.svg" alt="">
		{:else}
			<img src="icons/dir.svg" alt="">
		{/if}
		<button on:click={expandFolder}>{getNameByPath(path)}</button>
	</div>
	{#if expand}
		{#each directories as dir}
			<svelte:self path="{path}/{dir}" />
		{/each}
		{#each files as file}
			<File path="{path}/{file}" />
		{/each}
	{/if}
</div>

<style lang="scss">
	div {
		width: 100%;

		button {
			width: 100%;
			height: 30px;
			display: flex;
			align-items: center;
			background: inherit;
			border: none;
			color: #fff;
			font-size: 15px;
			font-family: monospace;
			cursor: pointer;
			transition: background-color 200ms;

			&:hover {
				background-color: #ffffff0c;
			}
		}

		img {
			width: 20px;
			height: 20px;
			margin-left: var(--marginL);
			margin-right: 12px;
		}
	}
</style>
