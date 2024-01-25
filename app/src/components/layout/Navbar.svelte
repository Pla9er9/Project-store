<script lang="ts">
	import { PUBLIC_API_URL } from '$env/static/public';
	import Avatar from '../Avatar.svelte';
	import LinkButton from './LinkButton.svelte';
	import SearchInput from './SearchInput.svelte';

	export let username: string | null;
</script>

<nav class="row">
	<a href="/" class="row home">
		<img src="icons/project.svg" alt="">
		<h1> Project store</h1>
	</a>
	<SearchInput />
	{#if username === null}
		<div id="authBar">
			<LinkButton text="Log in" link="/login" />
			<div class="line" />
			<LinkButton text="Register" link="/register" />
		</div>
	{:else}
		<a class="iconBtn" href="/new" style="margin-left: auto;">
			<img src="/icons/plus.svg" alt="">
		</a>
		<button class="iconBtn">
			<img src="/icons/notification.svg" alt="">
		</button>
		<a class="iconBtn" href="/settings">
			<img src="/icons/settings.svg" alt="">
		</a>
		<a href="/{username}" style="margin: 0 20px;">
			<Avatar
				size="40px"
				cursor="pointer"
				margin="0"
				imageUrl={`${PUBLIC_API_URL}/user/${username}/avatar`}
			/>
		</a>
	{/if}
</nav>

<style lang="scss">
	nav {
		width: 100%;
		padding: 10px 0;
		border-bottom: 1px solid #202020;

		.home {
			img {
				width: 22px;
				margin: 0 12px 0 25px;
			}

			h1 {
				margin: 0;
				margin-bottom: 4px;
				font-size: 20px;
				color: #fff;
				font-family: 'Syne', sans-serif;
			}
		}

		.iconBtn {
			width: 35px;
			height: 35px;
			display: flex;
			justify-content: center;
			align-items: center;
			background-color: inherit;
			border: none;
			margin: 0 12px;
			cursor: pointer;

			&:hover {
				border-radius: 50%;
				background-color: rgba(128, 128, 128, 0.055);
			}

			img {
				width: 21px;
			}
		}

		#authBar {
			display: flex;
			border: solid 1px #ffffff23;
			border-radius: 9px;
			margin-left: auto;
			margin-right: 25px;

			.line {
				width: 1px;
				height: 34px;
				background: #ffffff23;
			}
		}
	}
</style>
