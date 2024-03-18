<script lang="ts">
    export let text: string
    export let options: string[]
    export let value: string
    export let imgSrc: string
    export let onSelect: () => void = () => {}
</script>

<div>
    <img src="{imgSrc}" alt="icon">
    <p>{text}</p>
    <select bind:value on:change={onSelect}>
        {#each options as option}
            <option value="{option}">{option}</option>
        {/each}
    </select>
</div>

<style lang="scss">
    div {
        width: 242px;
        height: 40px;
        display: flex;
        margin: 10px 0;
        align-items: center;
        border-radius: 8px;
        border: solid 1px var(--lightBorder);

        img {
            width: 18px;
            margin-left: 30px;
            margin-right: 10px;
        }

        p {
            text-indent: 10px;
            font-family: 'Fira sans', sans-serif;
            font-size: 14px;
            color: #ffffff6b;
        }

        select {
            width: 90px;
            height: 22px;
            margin-left: auto;
            margin-right: 15px;
            background-color: #0c0b0b;
            border: solid 1px #ffffff1e;
            border-radius: 10px;
            color: rgb(221 222 223);
            outline: none;
            text-indent: 8px;
            font-family: monospace;

        }
    }
</style>