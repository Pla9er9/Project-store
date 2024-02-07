<script lang="ts">
    import Block from "./Block.svelte";

    export let name: string;
    export let date: string;
</script>

<Block
    link="/console/new"
    icon="/icons/application.svg"
    header="{name}"
    smallText="{date}"
    gradientColor="#bd7ef7"
/>
