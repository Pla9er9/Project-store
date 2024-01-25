<script lang="ts">
	import { goto } from '$app/navigation';
	import { page } from '$app/stores';
	import DirectoryView from '$components/project/files/DirectoryView.svelte';
	import File from '$components/project/files/File.svelte';

	export let data;
	let isFile: boolean = data.data.folders === undefined ? true : false;
	let path = $page.url.searchParams.get('path');
	if (path === null) {
		goto('/404');
	}

	const len = path ? path.length : 0;
	const getFoldersStructure = () => {
		let current = '';
		const folderStructure: string[] = [];

		for (let i = 0; i < len; i++) {
			if (path![i] === '/' && current !== '') {
				folderStructure.push(current);
				current = '';
			} else {
				current += path![i];
			}
		}
		folderStructure.push(current);

		return folderStructure;
	};
	const folderStructure = getFoldersStructure();
</script>

{#if isFile}
	<p>
		<a href=".">project</a>
		{#each folderStructure as folder, i (i)}
			/<a href="?path={folderStructure.slice(0, i + 1).join('/')}" data-sveltekit-reload
				>{folder.replaceAll('/', '')}</a
			>
		{/each}
	</p>
	<File code={data.data} />
{:else}
	<h1>Folder</h1>
	<DirectoryView data={data.data} slug={data.slug} />
{/if}

<style lang="scss">
	p {
		font-size: 18px;
		font-family: monospace;

		a {
			margin: 0 3px;
			color: var(--mainColor);

			&:first-child {
				margin: 0px;
			}
		}
	}
</style>
