<script lang="ts">
	import { PUBLIC_API_URL } from '$env/static/public';
	import { spaceStore } from '$lib/stores/spaceStore';
	import { get } from 'svelte/store';

	export let path: string;

	function getNameByPath(path: string): string {
		let res = '';
		for (let i = path.length - 1; i >= 0; i--) {
			if (path[i] == '/') {
				return res;
			} else {
				res = path[i] + res;
			}
		}
		return res;
	}

	async function loadFile() {
		const res = await fetch(`${PUBLIC_API_URL}/project/
            ${get(spaceStore).projectId}/files?path=${path}`);
		if (!res.ok) return;
		const file = await res.text();
		spaceStore.update((v) => {
			v.loadedFiles.set(path, file);
			return v;
		});
		loaded = true;
	}

	async function setFileToEdit() {
		if (!loaded) {
			await loadFile();
		}
		spaceStore.update((v) => {
			v.currentFile = fileName;
			return v;
		});
	}

	const fileName = getNameByPath(path);
	let loaded = false;

	let count = 0;
	for (let i = 0; i < path.length; i++) {
		if (path[i] === '/') count++;
	}
</script>

<button on:click={setFileToEdit} style="--marginL: {12 + count * 12}px">
	<img src="/icons/file.svg" alt="">
	{getNameByPath(path)}
</button>

<style lang="scss">
	button {
		width: 100%;
		height: 30px;
		margin: 2px 0;
		display: flex;
		align-items: center;
		background: inherit;
		border: none;
		color: #fff;
		font-size: 15px;
		font-family: monospace;
		cursor: pointer;
		transition: 200ms;

		&:hover {
			background-color: #ffffff0c;
		}

		img {
			width: 18px;
			height: 18px;
			margin-left: var(--marginL);
			margin-right: 12px;
		}
	}
</style>
