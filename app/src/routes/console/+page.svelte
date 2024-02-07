<script>
    import Application from "$components/console/Application.svelte";
    import NewApplication from "$components/console/NewApplication.svelte";

    let data = [
        ["Apple", "07.02.2024"],
        ["Google", "07.02.2024"],
        ["Facebook", "07.02.2024"],
    ];
</script>

<main class="column" style="align-items: flex-start;">
    <div class="row" style="align-self: flex-start;margin-bottom: 35px;">
        <img src="/icons/console.svg" alt="" style="margin: 0 15px 0 0;" />
        <h1>Dev console</h1>
    </div>
    <div class="column" style="align-items: flex-start;margin-left: 15px;">
        <div class="row">
            <img
                src="/icons/application.svg"
                alt=""
                style="width: 26px;margin: 0 15px;"
            />
            <h2>Applications</h2>
        </div>
        <div class="row">
            <NewApplication />
            {#each data as d}
                <Application name={d[0]} date={d[1]} />
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
            color: #fff;
            font-size: 38px;
            font-family: "Inter";
        }

        h2 {
            color: rgb(224, 209, 209);
            font-family: "Fira sans";
            font-size: 28px;
        }

        .row {
            width: 100%;
            max-width: 800px;
            flex-wrap: wrap;
            align-self: center;
        }
    }
</style>
