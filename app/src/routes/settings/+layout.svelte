<script>
    import Sidebar from "$components/settings/Sidebar.svelte";
</script>

<main>
    <Sidebar />
    <div style="margin-top: 20px;margin-left: 70px;">
        <slot />
    </div>
</main>

<style>
    main {
        width: 100vw;
        height: calc(100vh - 93px);
        display: flex;
    }
</style>