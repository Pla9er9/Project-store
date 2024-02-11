<script lang="ts">
	import { page } from '$app/stores';

	export let data;
	export let slug: string;

	let currentPath = $page.url.searchParams.get('path');
	if (currentPath === null) {
		currentPath = '';
	}
	currentPath += '/';
	const files = data.files;
	const directories = data.folders;
</script>

{#if directories.length > 0 || files.length > 0}
	<div class="directoryView">
		{#each directories as dir}
			<a href="/project/{slug}/file?path={currentPath}{dir}" data-sveltekit-reload>
				<img src="/icons/dir.svg" alt="">
				<p>{dir}</p>
			</a>
		{/each}
		{#each files as file}
			<a href="/project/{slug}/file?path={currentPath}{file}" data-sveltekit-reload>
				<img src="/icons/file.svg" alt="">
				<p>{file}</p>
			</a>
		{/each}
	</div>
{:else}
<div style="display:flex;flex-direction:column;align-items:center">
	<img src="/icons/file.svg" alt="">
	<p>No code added yet!</p>
</div>
{/if}

<style lang="scss">
	.directoryView {
		max-width: 1000px;
		width: 90%;
		margin: 0 auto;
		outline: solid 1px var(--lightBorder);
		border-radius: 5px;
		background-color: #0a0a0a00;
		margin-bottom: 40px;
		
		a {
			width: 100%;
			height: 55px;
			background-color: inherit;
			display: flex;
			align-items: center;
			border: none;
			border-bottom: solid 1px #333333;
			transition: 100ms ease-in-out;

			&:hover {
				border-radius: 5px;
				background-color: #4163fc05;
			}

			img {
				margin-left: 15px;
				margin-right: 14px;
				width: 20px;
				height: 20px;
			}

			p {
				color: #fff;
				font-size: 14px;
				cursor: pointer;
			}
		}
	}

	img {
		width: 120px;
		height: 120px;
		margin: 25px 0 20px 0;
	}

	p {
		font-size: 15px;
		font-family: monospace;
	}
</style>
