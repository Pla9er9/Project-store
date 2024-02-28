<script lang="ts">
    import { quintOut } from "svelte/easing";
    import { fly } from "svelte/transition";

	export let color: string;
	export let message: string;

	function getHexColor(color: string): string {
		switch (color) {
			case 'green':
				return '#31C48D';
			case 'blue':
				return '#76A9FA';
			case 'yellow':
				return '#FACA15';
			case 'red':
				return '#F98080';
		}
		return color;
	}
</script>

<div
	class="alert"
	style="
    --color: {getHexColor(color)};"
	transition:fly={{ delay: 50, duration: 300, x: 0, y: -200, opacity: 0.5, easing: quintOut }}
>
	<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path d="M11 17h2v-6h-2v6Zm1-8q.425 0 .713-.288T13 8q0-.425-.288-.713T12 7q-.425 0-.713.288T11 8q0 .425.288.713T12 9Zm0 13q-2.075 0-3.9-.788t-3.175-2.137q-1.35-1.35-2.137-3.175T2 12q0-2.075.788-3.9t2.137-3.175q1.35-1.35 3.175-2.137T12 2q2.075 0 3.9.788t3.175 2.137q1.35 1.35 2.138 3.175T22 12q0 2.075-.788 3.9t-2.137 3.175q-1.35 1.35-3.175 2.138T12 22Zm0-2q3.35 0 5.675-2.325T20 12q0-3.35-2.325-5.675T12 4Q8.65 4 6.325 6.325T4 12q0 3.35 2.325 5.675T12 20Zm0-8Z"/></svg>
	{message}
	<button on:click={() => (message = '')}>
		<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path d="m12 13.4l-4.9 4.9q-.275.275-.7.275t-.7-.275q-.275-.275-.275-.7t.275-.7l4.9-4.9l-4.9-4.9q-.275-.275-.275-.7t.275-.7q.275-.275.7-.275t.7.275l4.9 4.9l4.9-4.9q.275-.275.7-.275t.7.275q.275.275.275.7t-.275.7L13.4 12l4.9 4.9q.275.275.275.7t-.275.7q-.275.275-.7.275t-.7-.275L12 13.4Z"></svg>
	</button>
</div>

<style lang="scss">
	.alert {
		max-width: 700px;
		width: 95%;
		height: 60px;
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		margin: 20px auto;
		background: #111111;
		border: solid 1px #2d343d;
		display: flex;
		font-family: 'Fira sans';
		font-size: 14px;
		align-items: center;
		border-radius: 10px;
		color: var(--color);
		z-index: 99999999;

		svg {
			width: 20px;
			height: 20px;
			margin-left: 18px;
			margin-right: 40px;
			fill: var(--color);
		}

		button {
			width: 30px;
			height: 30px;
			display: flex;
			justify-content: center;
			align-items: center;
			margin-left: auto;
			margin-right: 15px;
			border: none;
			border-radius: 5px;
			background: inherit;
			cursor: pointer;

			&:hover {
				background-color: #f98080;
			}

			&:hover svg {
				fill: #000;
			}

			svg {
				fill: #f98080;
				margin: 0;
			}
		}
	}
</style>
