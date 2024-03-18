<script>
    export let data;

    import Application from "$components/console/Application.svelte";
    import NewApplication from "$components/console/NewApplication.svelte";
</script>

<svelte:head>
    <title>Console</title>
</svelte:head>

<main class="column" style="align-items: flex-start;">
    <h1 style="margin-bottom: 35px;">Dev console</h1>
    <div class="column applications" style="align-items: flex-start;">
        <div class="row">
            <img
                src="/icons/application.svg"
                alt=""
                style="width: 26px;margin: 0 15px;"
            />
            <h2>Applications</h2>
        </div>
        <div class="row" style="margin: 20px 0 0;">
            <NewApplication />
            {#each data.data as d}
                <Application name={d.name} date={d.created} id={d.id} />
            {/each}
        </div>
    </div>
</main>

<style lang="scss">
    main {
        width: 95%;
        max-width: 1250px;
        padding: 0 24px;
        box-sizing: border-box;
        height: 100px;
        margin: 40px auto;

        h1 {
            color: #dbd7d7;
            font-size: 38px;
            font-family: Inter, serif;
        }

        h2 {
            color: rgb(224 209 209);
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

    @media screen and (width <= 400px) {
        h1 {
            width: 100%;
            text-align: center;
        }
        
        .applications {
            margin-left: -30px;

            .row {
                align-self: center;
            }
        }
	}
</style>
