<script lang="ts">
	export let value: string;
	export let sendMessage: () => void;

	function keyPressed(key: KeyboardEvent) {
		if (key.key === 'Enter') {
			sendMessage();
			value = '';
		}
	}
</script>

<div>
	<input bind:value on:keypress={keyPressed} />
	<button on:click={sendMessage} type="submit">
		<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"
			><path d="M2.01 21L23 12L2.01 3L2 10l15 2l-15 2z" /></svg
		>
	</button>
</div>

<style lang="scss">
	div {
		max-width: 1400px;
		width: 95%;
		height: 40px;
		position: fixed;
		bottom: 15px;
		margin: 0 auto;
		display: flex;
		outline: solid 1px var(--lightBorder);
		border-radius: 10px;

		* {
			border-radius: 10px;
		}

		&:has(input:focus) {
			outline: solid 1px #c5c4c4;
		}

		input {
			width: 100%;
			height: 100%;
			border: none;
			text-indent: 15px;
			margin: 0;
			background-color: #000;
			outline: none;
			color: #fff;
			border-bottom-right-radius: 0;
		}

		button {
			width: 50px;
			height: calc(100% + 2px);
			display: flex;
			align-items: center;
			justify-content: center;
			font-family: 'Fira sans';
			transition: 250ms ease-in-out;
			background-color: #000;
			cursor: pointer;
			border: none;
			border-bottom-left-radius: 0;

			&:hover svg {
				fill: var(--mainColor);
			}

			svg {
				width: 20px;
				height: 20px;
				fill: #fff;
			}
		}
	}
</style>
