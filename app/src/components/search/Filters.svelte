<script lang="ts">
    import Select from "$components/forms/Select.svelte";

    export let query: string | null;
    export let type: string | null;

    if (type === "" || type === null) {
        type = "all";
    } else {
        type = type.replace("-", " ");
    }
</script>

<div class="row" style="width: 100%;">
    <img src="/icons/search_.svg" alt="" />
    <h1>{query}</h1>
    <Select
        bind:value={type}
        text="Filter"
        options={["all", "users", "project name", "project tag"]}
        imgSrc="/icons/filter.svg"
    />
</div>

<style lang="scss">
    div {
        width: 100%;
        height: 70px;
        margin-bottom: 8px;
        border: solid 1px var(--lightBorder);
        border-top-right-radius: 5px;
        border-top-left-radius: 5px;
        padding: 0 15px;
        box-sizing: border-box;

        h1 {
            font-family: "Fira sans";
            font-size: 20px;
            margin-right: auto;
        }

        img {
            width: 24px;
            margin: 0 18px;
        }
    }
</style>
