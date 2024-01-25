<script lang="ts">
	import Invitation from "$components/settings/Invitation.svelte";

	export let data;

	function deleteFromList(e) {
		let invitationId = e.detail.id
		data.data.filter((i) => i.id == invitationId)
	}

</script>

<main>
	<h1>Projects Invitations</h1>
	{#each data.data as invitation}
		<Invitation {invitation} on:deleteFromList={deleteFromList} />
	{/each}
</main>

<style lang="scss">
	main {
		display: flex;
		flex-direction: column;

		h1 {
			font-family: 'Inter', sans-serif;
			font-size: 35px;
			margin-bottom: 50px;
			color: #fff;
		}
	}
</style>
