<script lang="ts">
	import { fade } from 'svelte/transition';

	let words = ['SHARE', 'SHOW', 'HOST'];
	let currentIndex = 0;
	let current = words[currentIndex];
	let visible = true;

	const transitionDuration = 350;

	setInterval(async () => {
		currentIndex += 1;
		if (currentIndex >= words.length) {
			currentIndex = -1;
			return;
		}
		await new Promise((r) => setTimeout(r, transitionDuration));
		visible = false;
		await new Promise((r) => setTimeout(r, transitionDuration));
		visible = true;
		current = words[currentIndex];
	}, 2500);
</script>

<h1>
	{#if visible}
		<span transition:fade={{ duration: transitionDuration }}>{current}</span>
	{/if}
	your <br /> project
</h1>

<style lang="scss">
	h1 {
		text-align: center;
		color: #fff;
		font-family: 'Staatliches', sans-serif;
		font-size: 130px;
		font-weight: 100;
		margin-bottom: 0;
		line-height: 150px;


		span {
			background: -webkit-linear-gradient(#eee, #333);
			-webkit-background-clip: text;
			background-clip: text;
			-webkit-text-fill-color: transparent;
		}
	}

	@media screen and (max-width: 650px) {
		h1 {
			font-size: 19vw;
		}
	}
</style>
