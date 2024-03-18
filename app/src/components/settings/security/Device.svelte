<script lang="ts">
	export let device: any;

	import fetchHttp from "$lib/fetchHttp";
	import { tokenStore } from "$lib/stores/tokenStore";
	import { get } from "svelte/store";

	let nodeRef: Node;

	async function blockDevice(id: string) {
		await fetchHttp(`/account/loggedInDevices/${id}`, {
			method: "delete",
			token: get(tokenStore),
		});
		nodeRef.parentNode!.removeChild(nodeRef);
	}

	let hover = false;
</script>

<div class="device" bind:this={nodeRef}>
	<img src="/icons/_device.svg" alt="" style="width: 14px;" />
	<p>{device.userAgent}</p>
	<small>{device.loggedInTime.slice(0, 10)}</small>
	<button
		on:click={async () => await blockDevice(device.id)}
		on:mouseenter={() => (hover = true)}
		on:mouseleave={() => (hover = false)}
	>
		<img src="/icons/delete{hover ? '_danger' : ''}.svg" alt="" />
	</button>
</div>

<style lang="scss">
	.device {
		width: 100%;
		min-height: 55px;
		background-color: inherit;
		display: flex;
		align-items: center;
		margin: 8px 0;
		border-bottom: solid 1px var(--lightBorder);
		border-radius: 4px;
		box-sizing: border-box;
		padding: 0 15px;
		justify-content: center;

		p {
			height: max-content;
			font-family: sans-serif;
			font-size: 12px;
			color: rgb(200 198 198);
			margin-right: auto;
			margin-left: 18px;
		}

		small {
			min-width: max-content;
			margin: 0 10px;
			font-size: 12px;
			font-family: monospace;
			color: rgb(105 106 105);
		}

		button {
			width: 30px;
			height: 30px;
			background-color: inherit;
			border: none;
			cursor: pointer;
			display: flex;
			justify-content: center;
			align-items: center;
		}

		img {
			width: 18px;
		}
	}
</style>
