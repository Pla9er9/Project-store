<script lang="ts">
	import "../app.pcss";
    import Navbar from '$components/layout/Navbar.svelte';
    import { setContext } from 'svelte';
    import { alertStore, type Alert as alert } from '$lib/stores/alertStore';
    import Alert from '$components/layout/Alert.svelte';
    import { tokenStore } from '$lib/stores/tokenStore.js';
    import { afterNavigate, beforeNavigate, goto } from '$app/navigation';
    import Footer from '$components/layout/Footer.svelte';

    export let data;

    setContext('token', data.token);
    let currentAlert: alert;
    alertStore.subscribe((e) => {
		currentAlert = e;
	});
    tokenStore.update(e => {
		e = data.token
		return e
	})

    beforeNavigate(async ({to, cancel}) => {
		if (to?.route.id === "" && data.token) {
			cancel()
			await goto("home")
		}
	})

</script>

<Navbar username="{data.username}"></Navbar>
{#if currentAlert.message !== ''}
	<Alert bind:message="{currentAlert.message}" bind:color="{currentAlert.color}"></Alert>
{/if}
<div>
	<slot></slot>
</div>
<Footer></Footer>

<style>
	div {
		min-height: calc(100vh - 60px);
		height: max-content;
	}
</style>