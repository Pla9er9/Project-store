<script lang="ts">
    export let text: string
    export let options: string[]
    export let value: string
    export let imgSrc: string
</script>

<div>
    <img src="{imgSrc}" alt="icon">
    <p>{text}</p>
    <select bind:value>
        {#each options as option}
            <option value="{option}">{option}</option>
        {/each}
    </select>
</div>

<style lang="scss">
    div {
        width: 240px;
        height: 40px;
        display: flex;
        align-items: center;
        border-radius: 8px;
        border: solid 1px var(--lightBorder);

        img {
            width: 20px;
            margin-left: 15px;
            margin-right: 10px;
        }

        p {
            font-family: 'Fira sans';
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
            color: rgb(221, 222, 223);
            outline: none;
            text-indent: 8px;
            font-family: monospace;

        }
    }
</style>