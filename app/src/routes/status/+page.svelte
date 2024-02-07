<script>
    // @ts-nocheck
    import StatusRow from "$components/status/StatusRow.svelte";

    
    let data = [
        ["Auth service", true, "lock.svg"],
        ["Project service", true, "project_gray.svg"],
        ["Files service", false, "file.svg"],
        ["Chat service", false, "message.svg"],
        ["Search service", true, "search_gray.svg"],
        ["Database", true, "db.svg"],
    ]
</script>

<main class="column">
    <div class="row" style="width: 90%;">
        <img src="icons/status.svg" alt="">
        <h1>Server services status</h1>
    </div>
    <div class="row statusBoxes">
        {#each data as dt}
            <StatusRow serviceName={dt[0]} working={dt[1]} iconName={dt[2]} />
        {/each}
    </div>
</main>

<style lang="scss">
    main {
        width: 100%;
        max-width: 1200px;
        margin: 80px auto;

        h1 {
            width: 80%;
            font-family: 'Inter';
            font-weight: 100;
            font-size: 40px;
        }

        img {
            width: 45px;
            margin-right: 30px;
            animation-name: moving;
            animation-duration: 2.5s;
            animation-iteration-count: infinite;
        }

        @keyframes moving {
            0% {
                transform: rotate(0deg);
            }
            100% {
                transform: rotate(180deg);
            }
        }

        .statusBoxes {
            margin-top: 40px;
            justify-content: space-around;
            flex-wrap: wrap;
        }
    }
</style>
