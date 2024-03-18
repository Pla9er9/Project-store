<script lang="ts">
	export let onclick: () => void;
	export let cursor = "pointer"
</script>

<button class="linkButton" style="cursor: {cursor}" on:click={onclick}>
	<slot />
</button>

<style lang="scss">
	.linkButton {
		width: 160px;
		height: 40px;
		margin: 8px 5px;
		display: flex;
		align-items: center;
		background-color: #111;
		border: solid 1px #fff1;
		border-radius: 9px;

		&:hover {
			border: solid 1px #504f4f;
		}
	}
</style>
