<script lang="ts">
    import "../app.pcss";
    import Navbar from "$components/layout/Navbar.svelte";
    import { alertStore, type Alert as alert } from "$lib/stores/alertStore";
    import { toastStore } from "$lib/stores/toastStore";
    import Alert from "$components/layout/Alert.svelte";
    import { tokenStore } from "$lib/stores/tokenStore.js";
    import { beforeNavigate, goto } from "$app/navigation";
    import Footer from "$components/layout/Footer.svelte";
    import Toast from "$components/Toast.svelte";

    export let data;

    tokenStore.update((e) => {
        e = data.token;
        return e;
    });

    let currentAlert: alert;
    alertStore.subscribe((e) => {
        currentAlert = e;
    });

    beforeNavigate(async ({ to, cancel }) => {
        alertStore.update((a) => {
            a.message = "";
            return a;
        });
        toastStore.update((t) => {
            t.status = "none";
            return t;
        });
        if (to?.route.id === "" && data.token) {
            cancel();
            await goto("home");
        }
    });
</script>

{#if !data.hideUiParts}
    <Navbar username={data.username}></Navbar>
{/if}
{#if currentAlert.message !== ""}
    <Alert
        bind:message={currentAlert.message}
        bind:color={currentAlert.color}
    />
{/if}
<Toast />
<div>
    <slot />
</div>
{#if !data.hideUiParts}
    <Footer />
{/if}

<style>
    div {
        min-height: calc(100vh - 60px);
        height: max-content;
    }
</style>
