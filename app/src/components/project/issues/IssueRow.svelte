<script lang="ts">
	import Avatar from '$components/Avatar.svelte';
	import { PUBLIC_API_URL } from '$env/static/public';

	export let issue;
</script>

<a class="issueRow" href="issues/{issue.id}">
	{#if issue.isOpen}
		<div class="circle open" />
	{:else}
		<div class="circle closed" />
	{/if}
	<Avatar
		margin="0 12px 0 0"
		cursor="pointer"
		username={issue.createdBy.username}
		size="30px"
	/>
	<p>{issue.title}</p>
	<p>{issue.created.slice(0, 10)}</p>
</a>

<style lang="scss">
	.issueRow {
		width: 100%;
		display: flex;
		align-items: center;
		outline: solid 1px #ffffff38;
		margin: 1px 0;
		transition: background-color 100ms ease-in-out;

		&:hover {
			background-color: #ffffff09;
		}

		.circle {
			width: 15px;
			height: 15px;
			margin: 0 20px;
			border-radius: 50%;
		}
		.open {
			background: rgb(136, 252, 3);
			background: radial-gradient(circle, rgba(136, 252, 3, 1) 0%, rgba(39, 126, 0, 1) 100%);
		}

		.closed {
			background: rgb(164, 0, 251);
			background: radial-gradient(circle, rgba(164, 0, 251, 1) 0%, rgba(55, 0, 238, 1) 100%);
		}

		p {
			font-size: 18px;
			font-family: 'Fira sans';

			&:first-of-type {
				width: 60%;
				overflow-x: hidden;
			}

			&:last-child {
				width: 70px;
				text-align: center;
				margin-left: auto;
				margin-right: 25px;
				font-size: 14px;
			}
		}

		&:first-of-type {
			border-top-left-radius: 5px;
			border-top-right-radius: 5px;
		}

		&:last-child {
			border-bottom-left-radius: 5px;
			border-bottom-right-radius: 5px;
		}
	}
</style>
