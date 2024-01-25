<script lang="ts">
	let value = '';
	let focus = false;
</script>

<div id="searchInput">
	<div class="icon">
		<img src="/icons/search_.svg" alt="">
	</div>
	<input
		type="text"
		bind:value
		on:focusin={() => (focus = true)}
		on:focusout={() => (focus = false)}
		placeholder="Search"
	/>
	{#if focus && value != ""}
		<div id="searchItems" class="column">
			<button class="searchItem">
				<img src="/icons/search_.svg" alt="search icon">
				<p>{value}</p>
			</button>	
			<button class="searchItem">
				<img src="/icons/user.svg" alt="user icon">
				<p>Users by name {value}</p>
			</button>	
			<button class="searchItem">
				<img src="/icons/project.svg" alt="tag icon">
				<p>Projects by name {value}</p>
			</button>	
			<button class="searchItem">
				<img src="/icons/tag.svg" alt="tag icon">
				<p>Projects by tag {value}</p>
			</button>	
		</div>
	{/if}
</div>

<style lang="scss">
	#searchInput {
		width: max-content;
		display: flex;
		align-items: center;
		position: absolute;
		left: 0;
		right: 0;
		margin: 0 auto;

		input {
			width: 25vw;
			max-width: 300px;
			height: 24px;
			justify-self: center;
			text-align: center;
			color: #dddcdc;
			border: solid 1px #ffffff23;
			border-left: none;
			border-top-right-radius: 9px;
			border-bottom-right-radius: 9px;
			background-color: #141313;

			&:focus {
				outline: solid 1px rgba(220, 222, 224, 0.541);
				border-bottom-right-radius: 0px;
			}
		}

		#searchItems {
			position: absolute;
			top: 29px;
			left: 0;
			right: 0;
			margin: auto;
			align-items: end;

			.searchItem {
				width: 25vw;
				max-width: 305px;
				margin-left: auto;
				height: 40px;
				border: none;
				background-color: #141313;
				outline: solid 1px #ffffff1a;
				cursor: pointer;
				display: flex;
				align-items: center;

				&:last-of-type {
					border-bottom-left-radius: 5px;
					border-bottom-right-radius: 5px;
				}
				
				img {
					width: 20px;
					margin-left: 10px;
					margin-right: 20px;
				}

				p {
					width: 80%;
					text-align: start;
					color: rgb(255, 243, 243);
					font-size: 12px;
					font-family: 'Fira sans';
				}
			}
		}

		.icon {
			display: flex;
			align-items: center;
			justify-content: center;
			width: 30px;
			height: 26px;
			background-color: #141313;
			border: solid 1px #ffffff23;
			border-top-left-radius: 9px;
			border-bottom-left-radius: 9px;

			img {
				width: 60%;
				height: 60%;
			}
		}
	}
	@media screen and (max-width: 700px) {
		#searchInput {
			display: none;
		}
	}
</style>
