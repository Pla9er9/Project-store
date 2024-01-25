<script lang="ts">
	export let description: string;
</script>

<div id="description">
	,,{description}''
</div>

<style lang="scss">
	#description {
		// max-width: 300px;
		max-width: 100%;
		width: max-content;
		height: max-content;
		color: rgb(165, 164, 164);
		font-family: 'Inter';
		padding: 14px;
		border: solid 1px rgb(48, 48, 48);
		font-size: 14px;
		border-radius: 5px;
		display: flex;
		align-items: center;
	}
</style>
