<script lang="ts">
    export let value: string
</script>

<input class="versionInput" bind:value placeholder="Version like 1.0.0v">

<style lang="scss">
    .versionInput {
        width: 240px;
        height: 30px;
        background-color: inherit;
        border: solid 1px var(--lightBorder);
        border-radius: 5px;
        margin: 15px 0 25px 0px;
        display: flex;
        align-items: center;
        outline: none;
        text-indent: 20px;
        font-family: sans-serif;
        font-size: 14px;
        color: gray;
    }
</style>