<script lang="ts">
	export let text: string;
	export let link: string;
</script>

<a class="linkBtn" href={link}>
	<p>{text}</p>
</a>

<style lang="scss">
	.linkBtn {
		width: 120px;
		height: 32px;
		display: flex;
		align-items: center;
		justify-content: center;
		transition: 250ms;

		p {
			color: #d8d8d8;
			font-family: 'Fira Sans', sans-serif;
			font-size: 14px;
		}

		&:hover {
			background-color: #fff;
			border-radius: 9px;
		}

		&:hover p {
			color: #000;
		}
	}
</style>
