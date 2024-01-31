<script>

	import { goto } from "$app/navigation";

</script>
<main>
	<h1>404</h1>
	<h1>Page you are looking for wasnt found <br>
		<button on:click={() => goto("/")}>Back home</button>
	</h1>

</main>

<style lang="scss">
	main {
		max-width: 465px;
		width: 80%;
		height: max-content;
		position: absolute;
		left: 0;
		right: 0;
		margin: 0 auto;
		margin-top: 100px;

		h1 {
			text-align: center;
			margin: 0;

			&:first-child {
				background: -webkit-linear-gradient(#eee, #1f1f1f);
				-webkit-background-clip: text;
				background-clip: text;
				-webkit-text-fill-color: transparent;
				font-size: 220px;
			}

			button {
				margin-top: 35px;
				width: 120px;
				height: 35px;
				color: #161616;
				background-color: var(--mainColor);
				border: none;
				border-radius: 5px;
				cursor: pointer;
			}
		}
	}
	@media screen and (max-width: 480px) {
		h1:first-child {
			font-size: 160px !important;
		}
	}
</style>
