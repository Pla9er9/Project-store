<script lang="ts">
	export let data: DirectoryResponse;

	import FilesBar from '$components/project/space/FilesBar.svelte';
	import StateBar from '$components/project/space/ChangesBar.svelte';
	import CodeMirror from 'svelte-codemirror-editor';
	import { javascript } from '@codemirror/lang-javascript';
	import { githubDarkInit } from '@uiw/codemirror-theme-github';
	import { spaceStore, type Space } from '$lib/stores/spaceStore';
	import type { DirectoryResponse } from '$lib/models/file/DirectoryResponse';

	let space: Space | undefined = undefined;
	let currentFile: string | undefined = '';

	spaceStore.subscribe((value) => {
		space = value;
		currentFile = value.loadedFiles.get(value.currentFile);
	});
</script>

<div>
	<FilesBar directories={data.folders} files={data.files} />
	<div id="file">
		<p>
			{#if space !== undefined && space.currentFile != ""}
				<img src="/icons/file.svg" alt="">
				{space.currentFile}
			{/if}
		</p>
		<CodeMirror
			bind:value={currentFile}
			lang={javascript()}
			theme={githubDarkInit({
				settings: {
					gutterBackground: '#212121',
					background: '#0c0b0b',
					selection: 'gray',
					lineHighlight: '#7ef04110'
				}
			})}
			styles={{
				'&': {
					width: 'calc(100vw - 568px)',
					height: 'calc(100vh - 70px)'
				}
			}}
		/>
	</div>
	<StateBar />
</div>

<style lang="scss">
	div {
		display: flex;
		align-items: center;
		justify-content: center;

		#file {
			width: 100%;
			height: 100vh;
			display: flex;
			flex-direction: column;
			align-items: center;
			justify-content: space-around;

			img {
				width: 22px;
				height: 22px;
				margin-left: 15px;
				margin-right: 8px;
			}

			p {
				display: flex;
				align-items: center;
				font-family: monospace;
				font-size: 18px;
				width: 100%;
				height: 50px;
				margin: 0;
			}
		}
	}
</style>
