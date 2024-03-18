<script lang="ts">
	export let text: string;
	export let callback: () => void;
	export let isValid: boolean;
	export let iconUrl: string | undefined = undefined;
	export let width = "220px";

	if (iconUrl === undefined) {
		width = "240px";
	}
</script>

<div
	style="
	padding-left: {iconUrl === undefined ? '0' : '24px'};
	width: {width};
	--bg: {isValid ? '#88fc0323' : 'inherit'};
    --outlineColor: {isValid ? 'var(--mainColor)' : 'var(--lightBorder)'};
    --cursor: {isValid ? 'pointer' : 'not-allowed'};
    "
>
	{#if iconUrl !== undefined}
		<img src={iconUrl} alt="icon" />
	{/if}
	<button
		style="text-indent: {iconUrl !== undefined ? '-15px' : '35px'};"
		on:click={() => callback()}>{text}</button
	>
</div>

<style lang="scss">
	div {
		height: 40px;
		margin: 10px 0;
		display: flex;
		transition: 250ms ease-in-out;
		outline: solid 1px var(--outlineColor);
		border-radius: 8px;

		&:hover {
			background-color: #a7f74c34;
			outline: solid 1px var(--lightBorder);
		}

		button {
			width: 242px;
			height: 40px;
			border: none;
			background-color: transparent;
			color: #ffffffde;
			color: var(--mainColor);
			cursor: var(--cursor);
		}

		img {
			width: 18px;
		}
	}
</style>
