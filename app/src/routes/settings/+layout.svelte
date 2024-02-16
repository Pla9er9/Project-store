<script>
    import Sidebar from "$components/settings/Sidebar.svelte";
</script>

<main>
    <Sidebar />
    <div class="wrapper">
        <slot />
    </div>
</main>

<style lang="scss">
    main {
        min-height: 100vh;
        height: max-content;
        display: flex;
        flex-wrap: wrap;

        .wrapper {
            margin-top: 20px;
            margin-left: 70px;
        }
    }

    @media screen and (max-width: 930px) {
        main {
            flex-direction: column;

            .wrapper {
                margin: 60px auto 0 auto !important;
            }
        }
    }
</style>
