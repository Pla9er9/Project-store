<script lang="ts">
    export let icon: string
    export let label: string
    export let value: string
    export let width: string = "max-content"
</script>

<div class="panel" style="width: {width}">
    <img src="{icon}" alt="">
    <p class="label">{label}</p> 
    <p class="value">{value}</p>
</div>

<style lang="scss">
    .panel {
        min-width: 280px;
        width: 100%;
        height: 70px;
        margin: 10px 15px;
        padding: 15px;
        box-sizing: border-box;
        border: solid 1px var(--lightBorder);
        border-radius: 5px;
        display: flex;
        align-items: center;
        overflow: auto hidden;

        .label {
            font-family: 'Fira sans', sans-serif;
            margin-right: auto;
        }

        .value {
            max-width: 50%;
            width: max-content;
            max-height: 30px;
            overflow-x: auto;
            font-family: 'Fira sans', sans-serif;
            font-size: 14px;
            white-space: nowrap;
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
