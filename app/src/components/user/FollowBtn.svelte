<script lang="ts">
	export let following: boolean = false;
	export let onFollow: () => void;
	export let onUnfollow: () => void;
	export let width = '120px';
</script>

<button on:click={following ? onUnfollow : onFollow} class={following ? 'following' : ''} style="width: {width};"
	>{following ? 'Following' : 'Follow'}
</button>

<style lang="scss">
	button {
		height: 30px;
		background: rgb(0, 110, 255);
		color: #fff;
		border: none;
		border-radius: 4px;
		cursor: pointer;
	}
	.following {
		background-color: inherit;
		border: 1px solid #fff;
		color: #d8d8d8;
		transition: 200ms ease-in-out;

		&:hover {
			color: var(--danger);
			border: solid 1px var(--danger);
		}
	}
</style>
