<script lang="ts">
    export let icon: string
    export let label: string
    export let value: string
    export let width: string = "max-content"
</script>

<div class="panel" style="max-width: {width}">
    <img src="{icon}" alt="">
    <p class="label">{label}</p> 
    <p class="value">{value}</p>
</div>

<style lang="scss">
    .panel {
        width: 100vw;
        min-width: 250px;
        height: 70px;
        margin: 10px 15px;
        padding: 15px;
        box-sizing: border-box;
        border: solid 1px var(--lightBorder);
        border-radius: 5px;
        display: flex;
        align-items: center;
        overflow-x: auto;
        overflow-y: hidden;

        .label {
            font-family: 'Fira sans';
        }

        .value {
            max-width: 50%;
            margin: 0 10px 0 auto;
            width: max-content;
            overflow-x: auto;
            font-family: monospace;
            font-size: 15px;
            color: var(--mainColor);
        }

        p {
            width: max-content;
        }

        img {
            width: 22px;
            margin-right: 10px;
        }
    }
</style>
