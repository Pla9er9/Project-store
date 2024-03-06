<script lang="ts">
	import { goto } from '$app/navigation';
	import fetchHttp from '$lib/fetchHttp';
	import { tokenStore } from '$lib/stores/tokenStore';
	import { get } from 'svelte/store';
	import LinkButton from './LinkButton.svelte';
	import { PUBLIC_API_URL } from '$env/static/public';
	import CreatorsDialog from './Creators.svelte';
	import type { UserDtoSimple } from '$lib/models/user/UserDtoSimple';
    import type { ProjectDTO } from '$lib/models/project/ProjectDTO';

	export let info: ProjectDTO
	export let projectId: string;

	let creators: UserDtoSimple[] = info.creators;
	let likes: number = info.likes;
	let liked: boolean = info.liked;
	let latestRelease: string | null = info.latestRelease;

	const token = get(tokenStore);
	let showCreators = false

	async function like() {
		if (token === undefined) {
			await goto('login');
			return;
		}
		await fetchHttp(`/project/${projectId}/like`, {
			method: 'post',
			token: token
		});

		liked = true;
		likes += 1;
	}

	async function unlike() {
		if (token === undefined) {
			await goto('login');
			return;
		}
		await fetchHttp(`/project/${projectId}/unlike`, {
			method: 'put',
			token: token
		});

		liked = false;
		likes -= 1;
	}
</script>

<div class="linkButtons">
	<LinkButton onclick={async () => await goto(document.URL + '/issues')}>
		<img src="/icons/issue_.svg" alt="" />
		<p>Issues</p>
	</LinkButton>
	<LinkButton onclick={() => {
		showCreators = !showCreators
	}}>
		<img src="/icons/creators.svg" alt="" />
		<p>Creators</p>
		<span>{creators.length + 1}</span>
	</LinkButton>
	<LinkButton onclick={liked ? unlike : like}>
		<img src="/icons/{liked ? 'hearth' : 'hearthOutline'}.svg" alt="" />
		<p>{liked ? 'Liked' : 'Likes'}</p>
		<span>{likes}</span>
	</LinkButton>
	<LinkButton
		cursor={latestRelease ? 'pointer' : 'not-allowed'}
		onclick={latestRelease
			? () => {
					window.open(`${PUBLIC_API_URL}/project/${projectId}/release`, '_blank');
			  }
			: () => {}}
	>
		{#if latestRelease}
			<img src="/icons/checkmark.svg" alt="" />
		{:else}
			<img src="/icons/cross.svg" alt="" />
		{/if}
		<p style="font-size: 12px;">{latestRelease ? 'Release' : 'No releases'}</p>
		<span
			style="color: #acacac; font-size: 11px;
            ">{latestRelease ? latestRelease : ''}</span
		>
	</LinkButton>
</div>
{#if showCreators}
	<CreatorsDialog data={info} />
{/if}

<style lang="scss">
	.linkButtons {
		max-width: 730px;
		width: 100%;
		height: max-content;
		margin: 25px auto 40px 0;
		display: flex;
		flex-wrap: wrap;
		justify-content: start;

		span {
			color: #fff;
			margin-left: auto;
			margin-right: 10px;
		}

		img {
			width: 18px;
			height: 18px;
			margin: 0 15px 0 8px;
		}
	}
</style>
