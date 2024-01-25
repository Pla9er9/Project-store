<script>
	import { goto } from '$app/navigation';

	export let data;

	import AnimatedPanel from '$components/starterPage/AnimatedPanel.svelte';
	import MainText from '$components/starterPage/MainText.svelte';
	import { onMount } from 'svelte';

	onMount(() => {
		if (data.token) {
			goto('/home');
		}
	});
</script>

<main>
	<MainText />
	<AnimatedPanel />
</main>

<style lang="scss">
	main {
		display: flex;
		flex-direction: column;
		align-items: center;
		max-width: 1400px;
		margin: 0 auto;
	}
</style>
