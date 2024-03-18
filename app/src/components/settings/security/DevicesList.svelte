<script lang="ts">
    import type { DeviceDto } from "$lib/models/device/DeviceDto";
    import Device from "./Device.svelte";

    export let deviceList: DeviceDto[];
    export let expand = false;
</script>

<div id="deviceList">
    {#if expand}
        {#each deviceList as d}
            <Device device={d} />
        {/each}
    {/if}
</div>

<style lang="scss">
    #deviceList {
        width: 95%;
        margin-left: auto;
    }
</style>
