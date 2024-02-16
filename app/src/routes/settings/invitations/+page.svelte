<script lang="ts">
	import Invitation from "$components/settings/Invitation.svelte";

	export let data;

	function deleteFromList(e: CustomEvent) {
		let invitationId = e.detail.id;
		data.data.filter((i: any) => i.id == invitationId);
	}
</script>

<main class="column">
	<h1 class="settingsHeader">Projects Invitations</h1>
	{#if data.data.length > 0}
		{#each data.data as invitation}
			<Invitation {invitation} on:deleteFromList={deleteFromList} />
		{/each}
	{:else}
		<div class="column">
			<img src="/icons/cross.svg" alt="" />
			<p>No invitations found</p>
		</div>
	{/if}
</main>

<style>
	img {
		width: 100px;
	}
	p {
		font-family: "Fira sans";
	}
</style>
