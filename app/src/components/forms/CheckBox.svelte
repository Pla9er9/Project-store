<script lang="ts">
	export let label: string;
	export let value: boolean;
	export let marginLeft = '80px';
	export let border = 'solid 1px #ffffff23;';
</script>

<div
	class="checkBox"
	style="
		--background: {value ? 'var(--mainColor)' : '#ffffff08'};
		border: {border}"
>
	<slot />
	<p>{label}</p>
	<button style="margin-left: {marginLeft};" on:click={() => (value = !value)} />
</div>

<style lang="scss">
	.checkBox {
		display: flex;
		align-items: center;
		margin: 10px 0;
		width: 220px;
		height: 40px;
		border-radius: 8px;
		padding-left: 24px;

		p {
			color: #ffffff6b;
			font-family: 'Fira sans';
			font-size: 14px;
			margin: 0px;
			text-indent: 35px;
		}

		button {
			width: 18px;
			height: 18px;
			border: solid 1px #ffffff28;
			border-radius: 4px;
			background-color: var(--background);
			display: flex;
			justify-content: center;
			align-items: center;
			cursor: pointer;
		}
	}
</style>
