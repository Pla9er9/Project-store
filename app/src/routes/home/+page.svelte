<script>
    import ChatList from "$components/home/ChatList.svelte";
    import IssuesList from "$components/home/IssuesList.svelte";
    import LastProjects from "$components/home/LastProjects.svelte";
    import TrendingProjects from "$components/home/TrendingProjects.svelte";
</script>

<svelte:head>
    <title>Home</title>
</svelte:head>

<main class="row">
    <div>
        <ChatList />
    </div>
    <div class="column">
        <IssuesList />
        <LastProjects />
    </div>
    <div>
        <TrendingProjects />
    </div>
</main>

<style lang="scss">
    main {
        width: 100%;
        max-width: 1800px;
        margin: 20px auto;
        align-items: start;
        justify-content: center;

        .column {
            width: calc(100% - 800px);
            margin: 0 auto;
            min-width: 400px;
        }

        div:not(.column) {
            max-width: 370px;
            width: 90%;
            height: calc(100vh - 170px);
            margin: 15px;
        }
    }

    @media screen and (max-width: 1200px) {
        main {
            display: grid;
            grid-template-columns: 1fr;
            grid-template-rows: repeat(3, 1fr);
            grid-column-gap: 0px;
            grid-row-gap: 0px;
            height: 1570px;

            .column {
                width: 87%;
                min-width: 350px;
                grid-area: 1 / 1 / 2 / 2;
            }

            div:not(.column) {
                max-width: 80%;
                margin: 15px auto;
                height: 400px;
            }
        }
    }
</style>
