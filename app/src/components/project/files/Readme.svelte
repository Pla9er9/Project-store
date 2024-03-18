<script lang="ts">
	import DOMPurify from 'dompurify';

	export let readme: string;
</script>

{#if !(readme.trim() === "")}
	<div id="readme">
		<!-- eslint-disable-next-line -->
		{@html DOMPurify.sanitize(readme)}
	</div>
{:else}
	<div style="display:flex;flex-direction:column;align-items:center">
		<img src="/icons/astronaut.svg" alt="" />
		<p>This project does not <br /> contains readme</p>
	</div>
{/if}

<style>
	#readme {
		max-width: 1000px;
		width: calc(85% - 40px);
		margin: 0 auto;
		border-radius: 10px;
		background-color: var(--background);
		border: solid 1px var(--lightBorder);
		padding: 18px 20px;
		color: #dbdbdb;
		overflow-y: auto;
		font-family: sans-serif;
		word-break: break-all;
	}

	img {
		width: 140px;
		height: 140px;
		margin: 25px 0 20px;
	}

	p {
		font-size: 15px;
		margin: 18px 0 30px;
		text-align: center;
		font-family: monospace;
	}
</style>
