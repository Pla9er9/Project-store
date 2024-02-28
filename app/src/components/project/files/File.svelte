<script lang="ts">
    import langHighlight from "$lib/langHighlight";
    import Highlight, { LineNumbers } from "svelte-highlight";
    import { windows10 } from "svelte-highlight/styles";

    export let code: string;
    export let lang: string;
</script>

<svelte:head>
    {@html windows10}
</svelte:head>

<div class="code">
    <Highlight
        language={langHighlight(lang)}
        {code}
        let:highlighted
        --border-color="var(--lightBorder)"
    >
        <LineNumbers {highlighted} />
    </Highlight>
</div>

<style lang="scss">
    .code {
        margin: 0 auto;
        width: 90%;
        height: max-content;
        background-color: var(--background);
        border: solid 1px var(--lightBorder);
        border-radius: 5px;
    }
</style>
