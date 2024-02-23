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
	<button style="--marginL: {6 + count * 12}px" on:click={expandFolder}>
		{#if expand}
			<img src="/icons/dir_expand.svg" alt="">
		{:else}
			<img src="/icons/dir.svg" alt="">
		{/if}
		<p>{getNameByPath(path)}</p>
	</button>
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
			width: calc(100% - 10px);
			border-radius: 5px;
			margin: 2px 0;
			height: 30px;
			display: flex;
			align-items: center;
			background: inherit;
			border: none;
			cursor: pointer;
			transition: background-color 200ms;
			
			&:hover {
				background-color: #ffffff0c;
			}
			
			p {
				font-size: 15px;
				font-family: monospace;
			}
		}

		img {
			width: 18px;
			height: 20px;
			margin-left: var(--marginL);
			margin-right: 12px;
		}
	}
</style>
