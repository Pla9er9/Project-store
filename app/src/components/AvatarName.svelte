<script lang="ts">
	import { goto } from '$app/navigation';

	export let userdata;
	export let avatarSize = '100px';

	import { PUBLIC_API_URL } from '$env/static/public';
	import fetchHttp from '$lib/fetchHttp';
	import { get } from 'svelte/store';
	import Avatar from './Avatar.svelte';
	import FollowBtn from './user/FollowBtn.svelte';
	import { tokenStore } from '$lib/stores/tokenStore';
	import { onMount } from 'svelte';

	const token = get(tokenStore);

	var name = '';
	if (userdata.firstname !== null && userdata.lastname !== null) {
		name = userdata.firstname + ' ' + userdata.lastname;
	}

	async function follow() {
		if (token === undefined) {
			await goto('login');
			return;
		}
		await fetchHttp(`/user/${userdata.username}/follow`, {
			method: 'post',
			token: token
		});

		userdata.followed = true;
		userdata.followers += 1;
	}

	async function unfollow() {
		if (token === undefined) {
			await goto('login');
			return;
		}
		await fetchHttp(`/user/${userdata.username}/unfollow`, {
			method: 'put',
			token: token
		});

		userdata.followed = false;
		userdata.followers -= 1;
	}

	let followBtnWidth = '120px';

	onMount(() => {
		if (window.innerWidth <= 476) {
			followBtnWidth = '250px'
			avatarSize = '80px'
		}
		
		onresize = (e) => {
			if (window.innerWidth <= 476) {
				followBtnWidth = '250px'
				avatarSize = '80px'
			} else {
				followBtnWidth = '120px'
				avatarSize = '100px'
			}	
		}
	})

</script>

<div class="avatarName">
	<Avatar size={avatarSize} imageUrl="{PUBLIC_API_URL}/user/{userdata.username}/avatar" />
	<div class="textData">
		<h1>{userdata.username}</h1>
		<p>{name}</p>
		<div class="follows">
			<p>{userdata.followers} followers {userdata.following} following</p>
		</div>
	</div>
	{#if token !== undefined}
		{#if userdata.username != JSON.parse(atob(token.split('.')[1])).sub}
			<div id="followBtn_" style="margin-left: auto;">
				<FollowBtn
					width={followBtnWidth}
					following={userdata.followed}
					onFollow={follow}
					onUnfollow={unfollow}
				/>
			</div>
		{/if}
	{/if}
</div>

<style lang="scss">
	.avatarName {
		width: 100%;
		max-width: 900px;
		display: flex;
		flex-wrap: wrap;
		align-items: center;

		.textData {
			margin-left: 30px;
			margin-right: 40px;

			h1 {
				margin: 0;
				color: #fff;
				font-family: 'Fira sans';
			}

			p {
				margin: 0;
				margin-left: 1px;
				font-family: 'Fira sans';
				color: rgb(185, 185, 185);
			}

			.follows {
				display: flex;
				align-items: center;
				justify-content: space-around;

				p {
					margin-top: 4px;
					margin-left: -3px;
					color: var(--mainColor);
					font-size: 10px;
				}
			}
		}
	}

	@media screen and (max-width: 476px) {
		.avatarName {
			justify-content: center;
			margin-top: -15px;
		}

		#followBtn_ {
			width: 100%;
			display: flex;
			margin: 10px 0;
			justify-content: center;
		}
	}
</style>
