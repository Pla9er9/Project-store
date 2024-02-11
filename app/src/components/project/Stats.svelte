<script lang="ts">
	import Avatar from '$components/Avatar.svelte';
	import { PUBLIC_API_URL } from '$env/static/public';

	export let owner: string;
	export let isPrivate: boolean;
	export let createdDate: string;
	export let license: string | undefined;
	export let codeWeight: string;
</script>

<div class="stats">
	<div class="inline">
		<Avatar username={owner} size="20px" margin="0 12px 0 0" />
		<a href="/{owner}">{owner}</a>
	</div>
	{#if isPrivate === true}
		<div class="inline">
			<img src="/icons/lock.svg" alt="">
			<p>Private</p>
		</div>
	{:else}
		<div class="inline">
			<img src="/icons/public.svg" alt="">
			<p>Public</p>
		</div>
	{/if}
	<div class="inline">
		<img src="/icons/date.svg" alt="">
		<p>{createdDate.slice(0, 10)}</p>
	</div>
	{#if license !== undefined}
		<div class="inline">
			<img src="/icons/license.svg" alt="">
			<p>License {license}</p>
		</div>
	{/if}
	<div class="inline">
		<img src="/icons/disk.svg" alt="">
		<p>Source {codeWeight}</p>
	</div>
</div>

<style lang="scss">
	.stats {
		margin: 20px auto 20px 5px;
		display: flex;
		align-items: center;
		flex-wrap: wrap;
		--statsColor: #c9c7c7;

		.inline {
			display: flex;
			align-items: center;

			img {
				width: 18px;
				height: 18px;
				margin-right: 10px;
			}

			p,
			a {
				font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
				font-size: 14px;
				color: var(--statsColor);
				margin-right: 24px;
			}

			a:hover {
				color: var(--mainColor);
			}
		}
	}
</style>
