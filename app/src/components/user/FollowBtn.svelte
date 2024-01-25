<script lang="ts">
	export let following: boolean = false;
	export let onFollow: () => void;
	export let onUnfollow: () => void;
	export let width = '120px';
</script>

<button on:click={following ? onUnfollow : onFollow} class={following ? 'following' : ''} style="width: {width};"
	>{following ? 'Unfollow' : 'Follow'}
</button>

<style lang="scss">
	button {
		height: 30px;
		background: rgb(0, 110, 255);
		color: #fff;
		border: none;
		border-radius: 3px;
		cursor: pointer;
	}
	.following {
		background-color: inherit;
		border: 1px solid #16f145;
		color: #d8d8d8;
	}
</style>
