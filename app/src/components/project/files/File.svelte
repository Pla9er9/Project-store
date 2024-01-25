<script lang="ts">
	import go from 'svelte-highlight/languages/go';
	import Highlight from 'svelte-highlight';
	import { githubDark } from 'svelte-highlight/styles';

	export let code: string;
</script>

<svelte:head>
	{@html githubDark}
</svelte:head>

<div class="code">
	<Highlight language={go} {code} />
</div>

<style lang="scss">
	.code {
		margin: 0 auto;
		max-width: 1000px;
		width: 95%;
		height: max-content;
		background-color: #0d1117;
		border: solid 1px rgba(128, 128, 128, 0.233);
		border-radius: 5px;
	}
</style>
