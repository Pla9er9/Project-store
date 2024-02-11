<script>
    import { goto } from "$app/navigation";
    import BackBtn from "$components/BackBtn.svelte";
    import Panel from "$components/console/Panel.svelte";

    export let data;
    console.log(data.data);
</script>

<main>
    <div class="row" style="margin: 10px;">
        <BackBtn callback={() => goto("/console")} />
        <h1>{data.data.name}</h1>
        <small>{data.data.created.slice(0, 10)}</small>
    </div>
    <div class="row" style="flex-wrap: wrap;margin-top: 50px;">
        <Panel label="id" value={data.data.id} icon="/icons/personal_data_outline.svg" width="100%" />
        <Panel label="uses" value={data.data.numberOfUses} icon="/icons/plus.svg" />
        <Panel
        label="commercial"
        value={data.data.commercial}
        icon="/icons/dolar.svg"
        />
        <Panel label="redirect urls" value={[data.data.redirectUrls.join(" | "), "LOLdsadasdasdasdas"]} icon="/icons/personal_data_outline.svg" width="100%" />
    </div>
</main>

<style lang="scss">
    main {
        max-width: 800px;
        width: 100%;
        min-height: 480px;
        box-sizing: border-box;
        padding: 30px;
        border: solid 1px var(--lightBorder);
        border-radius: 5px;
        margin: 60px auto;

        .row {
            h1 {
                margin: 0 auto 0 0;
                font-family: "Fira sans";
            }

            small {
                font-size: 14px;
                font-family: sans-serif;
                color: rgb(162, 161, 161);
            }
        }
    }
</style>
