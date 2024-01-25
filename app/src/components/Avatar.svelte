<script lang="ts">
	export let imageUrl: string;
	export let size = '100px';
	export let margin = '30px 0px';
	export let cursor = 'auto';
</script>

<div
	id="avatar"
	style="
	background-image: url('{imageUrl}');
	--size: {size};	
	margin: {margin};
	cursor:{cursor};"
/>

<style>
	#avatar {
		width: 85vw;
		max-width: var(--size);
		height: 85vh;
		max-height: var(--size);
		border-radius: 100%;
		background-color: rgb(241, 241, 241);
		background-position: center;
		background-size: cover;
		background-repeat: no-repeat;
	}
</style>
