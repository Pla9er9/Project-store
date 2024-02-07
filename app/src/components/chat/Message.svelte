<script lang="ts">
	import Avatar from '$components/Avatar.svelte';
	import { PUBLIC_API_URL } from '$env/static/public';
	import type { ChatMessage } from '$lib/models/chat/ChatMessage';

	export let message: ChatMessage;
	export let username;

	let isMessageYours = message.senderId === username;
</script>

<div style="display: flex;align-items:center">
	{#if !isMessageYours}
		<a href="/{message.recipientId}">
			<Avatar
				cursor="pointer"
				margin="0 0 0 20px"
				username={message.recipientId}
				size="35px"
			/>
		</a>
	{/if}
	<div class="message {isMessageYours ? 'yourMessage' : 'notYourMessage'}">
		<p>{message.content}</p>
	</div>
	{#if isMessageYours}
		<Avatar
			margin="0 30px 0 0"
			username={message.recipientId}
			size="35px"
		/>
	{/if}
</div>

<style lang="scss">
	.message {
		width: max-content;
		height: max-content;
		max-width: 65%;
		word-break: break-all;
		display: flex;
		align-items: center;
		padding: 10px 15px;
		margin: 15px 0;
		border-radius: 15px;

		p {
			font-family: 'Fira sans';
			margin: 0;
			color: #e9e9e9;
		}
	}

	.yourMessage {
		margin-left: auto;
		margin-right: 15px;
		border: solid 1px #fff;
	}

	.notYourMessage {
		margin-left: 15px;
		margin-right: auto;
		border: solid 1px var(--lightBorder);
	}
</style>
