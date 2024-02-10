<script lang="ts">
    import Block from "./Block.svelte";

    export let name: string;
    export let date: string;
    export let id: string;
</script>

<Block
    link="/console/app/{id}"
    icon="/icons/application.svg"
    header="{name}"
    smallText="{date.slice(0, 10)}"
    gradientColor="gray"
/>
