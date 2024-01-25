<script lang="ts">
	import { goto } from '$app/navigation';
	import Input from '$components/forms/Input.svelte';
	import SubmitButton from '$components/forms/SubmitButton.svelte';
	import TextArea from '$components/forms/TextArea.svelte';
	import { PUBLIC_API_URL } from '$env/static/public';
	import { createEventDispatcher, getContext } from 'svelte';

	export let projectId: string;

	let title = '';
	let description = '';

	const dispatch = createEventDispatcher();

	let token = getContext('token');

	async function createNewIssue() {
		const body = JSON.stringify({
			title: title,
			description: description
		});

		const req = await fetch(PUBLIC_API_URL + '/project/' + projectId + '/issues', {
			method: 'POST',
			body: body,
			headers: {
				'Content-Type': 'application/json',
				Authorization: 'Bearer ' + token
			}
		});
		if (!req.ok) {
			alert('Error with creating project');
			return;
		}
		await goto('');
	}
</script>

<div id="newIssueForm">
	<div style="display: flex;align-items:center">
		<img src="/icons/open_issue.svg" alt="">
		<h1>New issue</h1>
	</div>
	<Input
		width="60%"
		bind:value={title}
		placeholder="Title"
		validator={(s) => (s.length > 3 && s.length < 50 ? '' : 'Wrong length')}
	>
		<img src="/icons/t_letter.svg" alt="">
	</Input>
	<TextArea
		width="55%"
		height="300px"
		maxlength={5000}
		bind:value={description}
		placeholder="Description"
	/>
	<br>
	<SubmitButton
		callback={() => {
			createNewIssue();
			dispatch('submit');
		}}
		isValid={true}
		text="Create"
	>
		<img src="/icons/checkmark.svg" alt="">
	</SubmitButton>
</div>

<style lang="scss">
	#newIssueForm {
		max-width: 500px;
		width: 95%;
		height: 630px;
		position: absolute;
		top: 100px;
		left: 0;
		right: 0;
		margin: 0 auto;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		background-color: #141414;
		border-radius: 10px;

		h1 {
			font-family: 'Fira sans';
			color: rgb(207, 206, 206);
			margin-left: 15px;
		}

		img {
			width: 24px;
		}
	}
</style>
