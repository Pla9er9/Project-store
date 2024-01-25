<script lang="ts">
	export let link: string;
</script>

<a id="downloadButton" href={link}>
	<img src="/icons/download.svg" alt="">
	<p>Download</p>
</a>

<style lang="scss">
	#downloadButton {
		position: relative;
		width: 110px;
		height: 40px;
		display: flex;
		align-items: center;
		justify-content: center;
		margin-right: 10px;
		float: right;
		transition: 150ms ease-in-out;
		outline: solid 1px rgb(85, 84, 84);
		border-radius: 5px;

		&:hover {
			background-color: #ffffff10;
		}

		img {
			width: 18px;
			height: 18px;
			margin-right: 8px;
		}

		p {
			font-size: 14px;
			text-align: center;
		}
	}
</style>
