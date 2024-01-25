<script lang="ts">
	export let device;

	import fetchHttp from "$lib/fetchHttp";
	import { tokenStore } from "$lib/stores/tokenStore";
	import { get } from "svelte/store";

	let nodeRef: Node

    async function blockDevice(id: string) {
        await fetchHttp(`/account/loggedInDevices/${id}`, {
            method: "delete",
            token: get(tokenStore)
        })
		nodeRef.parentNode!.removeChild(nodeRef)
    }

</script>

<div class="device" bind:this={nodeRef}>
	<div class="line" />
	<p><span style="color: #aaa9a9;">User-Agent</span> - {device.userAgent}</p>
	<small>{device.loggedInTime.slice(0, 10)}</small>
	<button on:click={async () => await blockDevice(device.id)}>
		<img src="/icons/delete.svg" alt="" />
	</button>

</div>

<style lang="scss">
	.device {
		height: 45px;
		border: solid 1px #797777;
		background-color: inherit;
		display: flex;
		align-items: center;
		justify-content: center;

		.line {
			margin-right: auto;
			width: 6px;
			height: 100%;
			background-color: rgba(66,255,164,.697);
		}

		p {
			width: 60%;
			font-family: sans-serif;
			font-size: 14px;
			overflow-y: auto;
			color: rgb(255, 255, 255);
			margin-right: auto;
		}

		small {
			margin-right: 10px;
			font-size: 12px;
			font-family: monospace;
			color: rgb(103, 107, 106);
		}

		button {
			width: 30px;
			height: 30px;
			background-color: inherit;
			border: none;
			margin-right: 5px;
			cursor: pointer;
			display: flex;
			justify-content: center;
			align-items: center;
			border-radius: 2px;

			&:hover {
				background-color: rgb(150, 60, 60);
			}

			img {
				width: 22px;
				height: 22px;
			}
		}
	}
</style>
