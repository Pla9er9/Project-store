<script lang="ts">
	export let value: string;
	export let sendMessage: () => Promise<void>;

	function keyPressed(key: KeyboardEvent) {
		if (key.key === 'Enter') {
			sendMessage();
			value = '';
		}
	}
</script>

<div>
	<textarea bind:value on:keypress={keyPressed} placeholder="New comment" />
	<button on:click={sendMessage} type="submit">
		<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"
			><path d="M2.01 21L23 12L2.01 3L2 10l15 2l-15 2z" /></svg
		>
	</button>
</div>

<style lang="scss">
	div {
		max-width: 1400px;
		width: 100%;
		height: 80px;
		bottom: 15px;
		margin: 0 auto;
		margin-top: 100px;
		margin-bottom: 40px;
		display: flex;
		outline: solid 1px rgba(128, 128, 128, 0.5);
		border-radius: 10px;

		* {
			border-radius: 10px;
		}

		textarea {
			width: 100%;
			height: 90%;
			border: none;
			padding: 10px 14px;
			margin: 0;
			background-color: inherit;
			outline: none;
			color: #fff;
			resize: none;
			border-bottom-right-radius: 0;
		}

		button {
			width: 50px;
			height: calc(100% + 2px);
			display: flex;
			align-items: center;
			justify-content: center;
			font-family: 'Fira sans';
			transition: fill 250ms ease-in-out;
			background-color: inherit;
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
