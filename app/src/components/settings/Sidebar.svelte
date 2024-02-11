<script lang="ts">
	import { goto } from '$app/navigation';
	import fetchHttp from '$lib/fetchHttp';
	import { tokenStore } from '$lib/stores/tokenStore';
	import { onMount } from 'svelte';
	import { get } from 'svelte/store';

	async function deleteAccount() {
		let sure = confirm(
			"Are you sure you want to delete account? You won't be able to undo the changes"
		);
		if (sure) {
			await fetchHttp('/account', {
				method: 'delete',
				token: get(tokenStore)
			});
			await goto(location.host);
		}
	}

	async function logout() {
		await fetchHttp(`${location.protocol}//${location.host}/logout`, {
			method: "post",
			apiUrlPrefix: false
		})
		
		await goto("/")
		location.reload()
	}

	let url: string = ""

	onMount(() => {
		url = `${location.protocol}//${location.host}/settings`
	})

</script>

<div id="sidebar" class="colun">
	<div class="row">
		<img src="/icons/settings.svg" alt="settings" />
		<a href="/settings">General</a>
	</div>
	<div class="row">
		<img src="/icons/security.svg" alt="security" />
		<a href="{url}/security">Security</a>
	</div>
	<div class="row">
		<img src="/icons/password.svg" alt="invitations" />
		<a href="{url}/change-password">Change <br> password</a>
	</div>
	<div class="row">
		<img src="/icons/letter.svg" alt="invitations" />
		<a href="{url}/invitations">Invitations</a>
	</div>
	<div class="row">
		<img src="/icons/logout.svg" alt="logout" />
		<button class="danger" on:click={logout}>Logout</button>
	</div>
	<div class="row">
		<img src="/icons/delete.svg" alt="delete account" />
		<button class="danger" on:click={deleteAccount}>Delete account</button>
	</div>
</div>

<style lang="scss">
	#sidebar {
		width: 240px;
		min-height: 100vh;
		height: 100%;
		border-right: 1px solid #202020;
		border-bottom: 1px solid #202020;
		border-bottom-right-radius: 5px;
		padding-top: 30px;
		align-items: start;

		.row {
			height: 40px;
			width: 70%;
			display: flex;
			align-items: center;
			margin: 22px 0 22px 30px;
			border-radius: 10px;

			img {
				width: 24px;
				margin-right: 15px;
			}

			a, button {
				font-size: 15px;
				font-family: sans-serif;
				color: #888888;
                cursor: pointer;
				transition: color 100ms ease-in-out;

                &:hover {
					color: #fff;
                }
			}

			button {
				background-color: inherit;
				border: none;
				padding: 0;
			}

			.danger:hover {
				color: rgb(228, 78, 78);
			}
		}
	}
</style>
