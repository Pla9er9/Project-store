<script lang="ts">
	export let value: string;
	export let placeholder: string;
	export let width = '210px';
	export let height = '113px';
	export let maxlength = 80;
	export let style = ""
</script>

<textarea
	style="max-width: {width};height: {height};{style}"
	{maxlength}
	spellcheck="false"
	bind:value
	{placeholder}
/>

<style lang="scss">
	textarea {
		width: 80vw;
		padding: 15px 25px;
		overflow: auto;
		color: #c2c0c0;
		outline: none;
		font-size: 14px;
		font-family: sans-serif;
		margin: 10px 0;
		border: solid 1px var(--lightBorder);
		border-radius: 8px;
		background-color: inherit;
		resize: none;
	}
</style>
