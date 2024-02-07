<script lang="ts">
	export let data;

	import { onMount } from 'svelte';
	import { CompatClient, Stomp, type IMessage } from '@stomp/stompjs';
	import SockJS from 'sockjs-client';
	import MessageInput from '$components/chat/MessageInput.svelte';
	import Message from '$components/chat/Message.svelte';
	import { goto } from '$app/navigation';

	let stompClient: CompatClient;
	let username: string;

	let message = '';
	let messages = [];

	if (data.token) {
		username = JSON.parse(atob(data.token.split('.')[1])).sub;
	} else {
		goto('login');
	}

	var authHeaders = {
		Authorization: `Bearer ${data.token}`
	}

	onMount(() => {
		const sock = new SockJS('http://localhost:8080/ws', {
			transportOptions: {
				'xhr-streaming': {
					headers: authHeaders
				}
			}
		});
		stompClient = Stomp.over(sock);
		stompClient.connect({ headers: authHeaders },onConnected, () => alert('Wystąpił błąd'));

		let body = document.querySelector('body');
		if (body) {
			body.style.backgroundColor = '#000';
			body.style.backgroundImage =
				'radial-gradient(circle, rgba(61, 61, 61, 0.1) 0%, rgba(0, 0, 0, 0) 100%)';
		}
	});

	function onConnected() {
		stompClient.subscribe(`/user/${username}/queue/messages`, onMessageReceived);
		stompClient.subscribe(`/user/public`, onMessageReceived);
	}
	function onMessageReceived(payload: IMessage) {
		var message = JSON.parse(payload.body);
		messages = [...messages, message];
	}

	async function sendMessage() {
		var messageContent = message.trim();
		if (messageContent && stompClient) {
			var chatMessage = {
				senderId: username,
				recipientId: data.username,
				content: messageContent
			};
			stompClient.send(`/app/chat`, {}, JSON.stringify(chatMessage));
			messages = [...messages, chatMessage];
		}
		message = '';
	}

	if (data.messages != undefined) {
		messages = data.messages;
	}
</script>

<main>
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
				font-family: 'Fira sans';
				text-align: center;
				color: gainsboro;
				width: 100%;
			}
		}
	}
</style>