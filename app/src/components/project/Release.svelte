<script lang="ts">
	export let projectId: string;
	export let latestRelease: string | null

	import FileInput from '$components/forms/FileInput.svelte';
	import SubmitButton from '$components/forms/SubmitButton.svelte';
	import fetchHttp from '$lib/fetchHttp';
	import { get } from 'svelte/store';
	import VersionInput from './VersionInput.svelte';
	import { tokenStore } from '$lib/stores/tokenStore';
	import { alertStore } from '$lib/stores/alertStore';
	import { goto } from '$app/navigation';
	import DeleteButton from '$components/DeleteButton.svelte';

	let files: FileList;
	let version: string = '';

	async function uploadRelease() {
		let formData = new FormData();
		let file = files.item(files.length - 1);
		if (file == null) {
			alertStore.update((a) => {
				a.color = 'red';
				a.message = 'You must add file';
				return a;
			});
			return;
		}
		formData.append('file', file);

		await fetchHttp(`/project/${projectId}/release/${version}`, {
			method: 'post',
			token: get(tokenStore),
			noContentType: true,
			stringify: false,
			body: formData
		});
		await goto(document.URL)
	}

	async function deleteRelease() {
		await fetchHttp(`/project/${projectId}/release`, {
			method: "delete",
			token: get(tokenStore)
		})
		location.reload()
	}

</script>

<div>
	<div style="display: flex;flex-direction:column;width:max-content">
		<FileInput directory={false} text="📌 Upload release" bind:value={files} />
		<VersionInput bind:value={version} />
		<SubmitButton
			text="Release"
			callback={uploadRelease}
			isValid={version.length > 0 && files.length > 0}
		>
			<img src="/icons/checkmark.svg" width="22px" alt="" />
		</SubmitButton>
	</div>
	{#if latestRelease}
		<div id="latestRelease">
			<img src="/icons/package.svg" alt="">
			<p>{latestRelease}</p>
			<DeleteButton callback={deleteRelease} />
		</div>
	{/if}
</div>

<style lang="scss">
	div {
		max-width: 720px;
		width: 100%;
		display: flex;
		justify-content: center;

		#latestRelease {
			width: 250px;
			height: 50px;
			display: flex;
			justify-content: space-between;
			align-items: center;
			color: #b8b8b8;
			font-size: 16px;
			font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
			border: 1px solid #ffffff23;
			border-radius: 5px;
			margin-left: 50px;
			
			p {
				width: 45%;
				background-color: red;
				text-align: center;
				overflow-x: auto;
			}

			img {
				width: 26px;
				height: 26px;
				margin: 0 12px;
			}
		}
	}
</style>
