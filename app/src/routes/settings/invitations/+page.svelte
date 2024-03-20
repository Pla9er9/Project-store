<script lang="ts">
	import Invitation from "$components/settings/Invitation.svelte";
    import type { InvitationDto } from "$lib/models/invitation/InvitationDto.js";

	export let data;

	let invitations: InvitationDto[] = data.data

	function deleteFromList(e: CustomEvent) {
		let invitationId = e.detail.id;
		invitations.filter((i: InvitationDto) => i.id == invitationId);
	}
</script>

<main class="column">
	<h1 class="settingsHeader">Projects Invitations</h1>
	{#if invitations.length > 0}
		{#each invitations as invitation}
			<Invitation {invitation} on:deleteFromList={deleteFromList} />
		{/each}
	{:else}
		<div class="column">
			<p>❌ No invitations found</p>
		</div>
	{/if}
</main>

<style>
	p {
		font-family: "Fira sans", sans-serif;
	}
</style>
