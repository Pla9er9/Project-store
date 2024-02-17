<script lang="ts">
	export let data;

	import { onMount } from "svelte";
	import { CompatClient, Stomp, type IMessage } from "@stomp/stompjs";
	import SockJS from "sockjs-client";
	import MessageInput from "$components/chat/MessageInput.svelte";
	import Message from "$components/chat/Message.svelte";
	import { goto } from "$app/navigation";

	let stompClient: CompatClient;
	let username: string;

	let message = "";
	let messages: any[] = [];

	if (data.token) {
		username = JSON.parse(atob(data.token.split(".")[1])).sub;
	} else {
		goto("login");
	}

	var authHeaders = {
		Authorization: `Bearer ${data.token}`,
	};

	onMount(() => {
		const body = document.querySelector("body");
		const sock = new SockJS("http://localhost:8080/ws", {
			transportOptions: {
				"xhr-streaming": {
					headers: authHeaders,
				},
			},
		});
		stompClient = Stomp.over(sock);
		stompClient.connect({ headers: authHeaders }, onConnected, () =>
			alert("Wystąpił błąd"),
		);

		if (body) {
			body.style.overflow = "hidden";
		}
		const messagesDiv = document.querySelector(".main");
		if (messagesDiv) {
			messagesDiv.scrollTo(0, messagesDiv.scrollHeight);
		}
	});

	function onConnected() {
		stompClient.subscribe(
			`/user/${username}/queue/messages`,
			onMessageReceived,
		);
		stompClient.subscribe(`/user/public`, onMessageReceived);
	}
	async function onMessageReceived(payload: IMessage) {
		var message = JSON.parse(payload.body);
		messages = [...messages, message];
		await new Promise((r) => setTimeout(r, 50));
		const messagesDiv = document.querySelector(".main");
		if (messagesDiv) {
			messagesDiv.scrollTo(0, messagesDiv.scrollHeight);
		}
	}

	async function sendMessage() {
		var messageContent = message.trim();
		if (messageContent && stompClient) {
			var chatMessage = {
				senderId: username,
				recipientId: data.username,
				content: messageContent,
			};
			stompClient.send(`/app/chat`, {}, JSON.stringify(chatMessage));
			messages = [...messages, chatMessage];
			await new Promise((r) => setTimeout(r, 50));
			const messagesDiv = document.querySelector(".main");
			if (messagesDiv) {
				messagesDiv.scrollTo(0, messagesDiv.scrollHeight);
			}
		}
		message = "";
	}

	if (data.messages != undefined) {
		messages = data.messages;
	}
</script>

<svelte:head>
	<title>Chat - {data.username}</title>
</svelte:head>

<main class="main">
	<div class="messages">
		{#if messages.length === 0}
			<h1>There is no <br /> messages yet</h1>
		{:else}
			{#each messages as message}
				<Message {message} {username} />
			{/each}
		{/if}
	</div>
	<MessageInput bind:value={message} sendMessage={() => sendMessage()} />
</main>

<style lang="scss">
	main {
		width: 98vw;
		height: calc(100vh - 225px);
		margin: 0 auto;
		margin-top: 60px;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		overflow-y: auto;

		.messages {
			margin-top: auto;
			width: 100%;
			height: 100%;

			h1 {
				font-family: "Fira sans";
				text-align: center;
				color: gainsboro;
				width: 100%;
			}
		}
	}
</style>
