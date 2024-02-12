<script lang="ts">
	import { goto } from "$app/navigation";
	import { page } from "$app/stores";
	import DirectoryView from "$components/project/files/DirectoryView.svelte";
	import File from "$components/project/files/File.svelte";

	export let data;
	let isFile: boolean = data.data.folders === undefined ? true : false;
	let path = $page.url.searchParams.get("path");
	if (path === null) {
		goto("/404");
	}

	const len = path ? path.length : 0;
	const getFoldersStructure = () => {
		let current = "";
		const folderStructure: string[] = [];

		for (let i = 0; i < len; i++) {
			if (path![i] === "/" && current !== "") {
				folderStructure.push(current);
				current = "";
			} else {
				current += path![i];
			}
		}
		folderStructure.push(current);

		return folderStructure;
	};
	const folderStructure = getFoldersStructure();
</script>

<main class="column">
	<div class="row">
		<a href=".">..</a>
		{#each folderStructure as folder, i (i)}
			<img src="/icons/arrow_right.svg" alt="" />
			<a
				href="?path={folderStructure.slice(0, i + 1).join('/')}"
				data-sveltekit-reload>{folder.replaceAll("/", "")}</a
			>
		{/each}
	</div>
	{#if isFile}
		<File code={data.data} />
	{:else}
		<DirectoryView data={data.data} slug={data.slug} />
	{/if}
</main>

<style lang="scss">
	main {
		width: 90%;
		max-width: 900px;
		margin: 60px auto;

		.row {
			height: 30px;
			margin: 15px auto 15px 15px;

			img {
				width: 20px;
				margin: 0 3px;
			}
	
			a {
				width: 100%;
				font-family: "Fira sans";
				font-size: 15px;
				color: #dfdcdc;
				transition: text-decoration 150ms;

				&:first-child {
					margin: 0px;
				}
	
				&:hover {
					text-decoration: underline;
				}
	
				&:last-of-type {
					color: rgb(0, 81, 255);
				}
			}
		}
	}
</style>
