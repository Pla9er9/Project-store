<script lang="ts">
	export let label: string;
	export let value: boolean;
	export let marginLeft = "80px";
	export let border = "solid 1px var(--lightBorder);";
</script>

<div
	class="checkBox"
	style="
		--background: {value ? 'var(--mainColor)' : '#ffffff08'};

		border: {border}"
>
	<slot />
	<p>{label}</p>
	<button style="margin-left: {marginLeft};" on:click={() => (value = !value)}>
		{#if value}
			<img src="/icons/checkmark_dark.svg" alt="">
		{/if}
	</button>
</div>

<style lang="scss">
	.checkBox {
		display: flex;
		align-items: center;
		margin: 10px 0;
		width: 218px;
		height: 40px;
		border-radius: 8px;
		padding-left: 24px;

		p {
			color: #ffffff6b;
			font-family: "Fira sans", sans-serif;
			font-size: 14px;
			margin: 0;
			text-indent: 15px;
		}

		button {
			width: 17px;
			height: 17px;
			border: solid 1px #ffffff28;
			border-radius: 6px;
			background-color: var(--background);
			display: flex;
			justify-content: center;
			align-items: center;
			cursor: pointer;
			transition: min-width 100ms ease;

			img {
				min-width: 12px;
			}
		}
	}
</style>
