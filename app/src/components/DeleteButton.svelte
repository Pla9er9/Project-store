<script lang="ts">
	export let callback: () => void;
</script>

<button on:click={callback}>delete</button>

<style>
	button {
		width: 60px;
		margin: 0 10px;
		height: 30px;
		background-color: #cc5050;
		border-radius: 4px;
		cursor: pointer;
		border: none;
	}
</style>
