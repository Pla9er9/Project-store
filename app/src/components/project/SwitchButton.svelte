<script lang="ts">
	export let values: string[];
	export let currentValue: string;
</script>

<div style="margin-top: 40px;">
	{#each values as value}
		<button
			style="background: {currentValue === value ? '#8eed4521' : 'inherit'}"
			on:click={() => (currentValue = value)}>{value}</button
		>
	{/each}
</div>

<style lang="scss">
	button {
		width: 120px;
		height: 35px;
		margin-bottom: 50px;
		border: none;
		color: #fff;
		font-family: 'Fira sans';
		outline: solid 1px #ffffff23;
		cursor: pointer;

		--margin: 6px;

		&:first-child {
			border-top-left-radius: var(--margin);
			border-bottom-left-radius: var(--margin);
		}

		&:last-child {
			border-top-right-radius: var(--margin);
			border-bottom-right-radius: var(--margin);
		}
	}
</style>
