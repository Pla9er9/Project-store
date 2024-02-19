<script lang="ts">
    export let width = "120px";
    export let height = "35px";
    export let outline = false;
    export let onClick: () => void;
    export let text: string;
</script>

<button
    on:click={onClick}
    style="width: {width};height: {height}"
    class={outline ? "outline" : ""}
>
    {text}
</button>

<style lang="scss">
    button {
        cursor: pointer;
        transition: 150ms ease-in-out;
        border-radius: 5px;
        background-color: var(--mainColor);
        color: #000;
        border: none;
        font-size: 13px;
    }

    .outline {
        background-color: inherit;
        border: solid 1px var(--mainColor);
        color: var(--mainColor);

        &:hover {
            background-color: var(--mainColor);
            color: #000;
        }
    }
</style>
