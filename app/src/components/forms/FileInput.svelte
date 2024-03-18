<script lang="ts">
	export let onChange: (e: Event) => void = () => {};
	export let value: FileList;
	export let text = '👾 Click to upload project code'
	export let directory = true
</script>

<div style="width: 250px; --text: '{text}';">
	{#if directory}
    	<input type="file" bind:files={value} webkitdirectory multiple on:change={onChange} class="custom-file-input" />
	{:else}
		<input type="file" bind:files={value} accept=".zip,.exe" on:change={onChange} class="custom-file-input" />
	{/if}
</div>

<style lang="scss">
	.custom-file-input {
        margin: 10px 0;
		color: transparent;
	}

	.custom-file-input::-webkit-file-upload-button {
		visibility: hidden;
	}

	.custom-file-input::before {
		width: 225px;
		content: var(--text);
		display: inline-block;
		border: 1px solid var(--lightBorder);
		border-radius: 5px;
		padding: 8px;
		outline: none;
		white-space: nowrap;
		-webkit-user-select: none;
		user-select: none;
		cursor: pointer;
		text-align: center;
        font-family: 'Fira sans', sans-serif;
        font-size: 13px;
        color: rgb(221 222 223);
	}

	.custom-file-input:active {
		outline: 0;
	}

</style>
