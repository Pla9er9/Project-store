<script lang="ts">
	export let text: string;
	export let callback: () => void;
	export let isValid: boolean;
</script>

<div
	style="
    --bg: {isValid ? '#88fc0323' : 'inherit'};
    --outlineColor: {isValid ? 'var(--mainColor)' : '#ffffff23'};
    --cursor: {isValid ? 'pointer' : 'not-allowed'};
    "
>
	<slot />
	<button on:click={() => callback()}>{text}</button>
</div>

<style lang="scss">
	div {
		width: 220px;
		height: 40px;
		margin: 10px 0;
		display: flex;
		align-items: center;
		transition: 250ms;
		background-color: var(--bg);
		outline: solid 1px var(--outlineColor);
		border-radius: 8px;
		padding-left: 24px;

		button {
			width: 242px;
			height: 40px;
			border: none;
			text-align: start;
			text-indent: 35px;
			background-color: rgba(0, 0, 0, 0);
			color: #ffffffde;
			cursor: var(--cursor);
		}
	}
</style>
