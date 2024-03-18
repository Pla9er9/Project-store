<script lang="ts">
    export let callback: () => void
    export let margin = "0 20px 0 0"
</script>


<button class="backBtn" on:click={callback} style="margin: {margin};">
    <img src="/icons/btn_back.svg" alt="" />
</button>

<style lang="scss">
    .backBtn {
        width: 40px;
        height: 40px;
        border-radius: 100%;
        background: inherit;
        border: none;
        cursor: pointer;
        display: flex;
        align-items: center;
        justify-content: center;
        transition: 150ms ease-in-out;

        &:hover {
            background: rgba(128 128 128 / 6.2%);
        }

        img {
            width: 18px;
            margin-left: 6px;
        }
    }
</style>
