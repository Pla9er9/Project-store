<script>
    import Application from "$components/console/Application.svelte";
    import NewApplication from "$components/console/NewApplication.svelte";

    let data = [
        ["Apple", "07.02.2024"],
        ["Google", "07.02.2024"],
        ["Facebook", "07.02.2024"],
    ];
</script>

<svelte:head>
    <title>Console</title>
</svelte:head>

<main class="column" style="align-items: flex-start;">
    <h1 style="margin-bottom: 35px;">Dev console</h1>
    <div class="column" style="align-items: flex-start;">
        <div class="row">
            <img
                src="/icons/application.svg"
                alt=""
                style="width: 26px;margin: 0 15px;"
            />
            <h2>Applications</h2>
        </div>
        <div class="row" style="margin: 20px 0 0 50px;">
            <NewApplication />
            {#each data as d}
                <Application name={d[0]} date={d[1]} id="213" />
            {/each}
        </div>
    </div>
</main>

<style lang="scss">
    main {
        width: 100%;
        max-width: 1250px;
        padding: 0 24px;
        box-sizing: border-box;
        height: 100px;
        margin: 40px auto;

        h1 {
            color: #dbd7d7;
            font-size: 38px;
            font-family: "Inter";
        }

        h2 {
            color: rgb(224, 209, 209);
            font-family: sans-serif;
            font-weight: 100;
            font-size: 26px;
        }

        .row {
            width: 100%;
            max-width: 800px;
            flex-wrap: wrap;
            align-self: center;
        }
    }
</style>
