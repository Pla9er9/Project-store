<script>
    import { goto } from "$app/navigation";
    import BackBtn from "$components/BackBtn.svelte";
    import NewSecretPanel from "$components/console/NewSecretPanel.svelte";
    import Panel from "$components/console/Panel.svelte";
    import Button from "$components/Button.svelte"
    import fetchHttp from "$lib/fetchHttp";
    import { tokenStore } from "$lib/stores/tokenStore";
    import { get } from "svelte/store";
    import { alertStore } from "$lib/stores/alertStore";

    export let data;

    async function deleteApplication() {
        const res = await fetchHttp(`/dev/application/${data.data.id}`, {
            token: get(tokenStore),
            method: "delete"
        })
        if (!res.ok) {
            alertStore.update(a => {
                a.message = "Could not delete application, try later"
                a.color = "red"
                return a
            })
            return
        }
        await goto("/console")
    }

</script>

<main>
    <div class="row" style="margin: 10px;">
        <BackBtn callback={() => goto("/console")} />
        <h1>{data.data.name} i taka nazwa dluga</h1>
        <small>{data.data.created.slice(0, 10)}</small>
    </div>
    <div class="row" style="flex-wrap: wrap;margin-top: 50px;">
        <Panel label="id" value={data.data.id} icon="/icons/personal_data_outline.svg" width="100%" />
        <Panel label="uses" value={data.data.numberOfUses} icon="/icons/plus.svg" width="46%" />
        <Panel
        label="commercial"
        value={data.data.commercial}
        icon="/icons/dolar.svg"
        width="46%"
        />
        <Panel label="redirect urls" value={data.data.redirectUrls.join(" ")} icon="/icons/personal_data_outline.svg" width="100%" />
        <NewSecretPanel applicationId={data.slug} />
        <div class="row btns">
            <Button text="Edit" onClick={() => {}} outline={true}  width="105px" />
            <Button text="Delete" onClick={deleteApplication} outline={true} width="105px" />
        </div>
    </div>
</main>

<style lang="scss">
    main {
        max-width: 800px;
        width: 90%;
        min-height: 480px;
        box-sizing: border-box;
        padding: 25px 10px;
        border: solid 1px var(--lightBorder);
        border-radius: 5px;
        margin: 60px auto;

        .row {
            h1 {
                overflow-x: auto;
                margin: 0 auto 0 0;
                font-family: "Fira sans";
                font-size: 28px;
                white-space: nowrap;
            }

            small {
                min-width: max-content;
                font-size: 14px;
                font-family: sans-serif;
                color: rgb(162, 161, 161);
                margin-left: 15px;
            }
        }

        .btns {
            width: 230px;
            justify-content: space-between;
            margin: 10px 15px;
        }
    }
</style>
