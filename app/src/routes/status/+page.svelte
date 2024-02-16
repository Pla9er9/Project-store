<script>
    // @ts-nocheck
    import StatusRow from "$components/status/StatusRow.svelte";
    
    export let data

    let _data = [
        ["Auth service", data.res.auth, "lock.svg"],
        ["Project service", data.res.project, "project_gray.svg"],
        ["Files service", data.res.files, "file.svg"],
        ["Chat service", data.res.chat, "message.svg"],
        ["Search service", data.res.search, "search_gray.svg"],
        ["Database", data.res.database, "db.svg"],
    ]
</script>

<svelte:head>
    <title>Server status</title>
</svelte:head>

<main class="column">
    <div class="row" style="width: 90%;">
        <img src="icons/status.svg" alt="">
        <h1>Server services status</h1>
    </div>
    <div class="row statusBoxes">
        {#each _data as dt}
            <StatusRow serviceName={dt[0]} working={dt[1]} iconName={dt[2]} />
        {/each}
    </div>
</main>

<style lang="scss">
    main {
        width: 100%;
        max-width: 1200px;
        margin: 40px auto;

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

    @media screen and (max-width: 568px) {
		h1 {
            font-size: 30px !important;
        }
	}
</style>
