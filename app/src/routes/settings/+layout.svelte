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
        min-height: 100vh;
        height: max-content;
        display: flex;
    }
</style>
