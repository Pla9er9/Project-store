<script lang="ts">
	export let menuVisible: boolean;
	export let mouseX: number;
	export let mouseY: number;
</script>

<div
	class="rightClickMenu"
	style="
		display: {menuVisible ? 'block' : 'none'};
		left: {mouseX}px;
		top: {mouseY}px;
	"
>
	<button>
		<img src="/icons/file.svg" alt="file icon">
		<p>New file</p>
	</button>
	<button>
		<img style="width: 17px;" src="/icons/dir.svg" alt="directory icon">
		<p>New directory</p>
	</button>
</div>

<style lang="scss">
	.rightClickMenu {
		position: absolute;
		width: 200px;
		height: 95px;
		background-color: #161616;
		border: solid 1px #808080;
		border-radius: 4px;
		z-index: 999;

		button {
			width: 100%;
			height: 40px;
			margin: 5px auto;
			border: solid 1px #ffffff31;
			border: none;
			display: flex;
			background-color: inherit;
			align-items: center;
			font-size: 13px;
			color: #fff;
			cursor: pointer;

			&:hover {
				background-color: #1f1e1e;
			}

			img {
				width: 16px;
				height: 16px;
				margin: 0 10px 0 4px;
			}
		}
	}
</style>
