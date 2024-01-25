<script lang="ts">
	import Device from "./Device.svelte";

    export let deviceList
    let expand = false
</script>

<div id="deviceList">
    <button on:click={() => expand = !expand}>
        <img src="/icons/eye.svg" alt="">
        <p>{expand ? "Hide" : "Show"}</p>
    </button>
    {#if expand}
        {#each deviceList as d}
            <Device device={d} />
        {/each}
    {/if}
</div>

<style lang="scss">
    #deviceList {
        margin-left: 30px;

        button {
            width: 96vw;
            max-width: 450px;
            height: 40px;
            border: solid 1px #e7e5e5;
            background-color: inherit;
            color: #e7e5e5;
            cursor: pointer;
            display: flex;
            align-items: center;
            justify-content: center;

            p {
                margin-right: auto;
            }

            img {
                width: 20px;
                margin-left: 8px;
                margin-right: auto;
            }
        }
    }
</style>