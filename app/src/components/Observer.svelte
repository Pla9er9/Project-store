<script lang="ts">
    import { onMount } from "svelte";

    export let onvisible: () => void;
    
    onMount(() => {
        let observer = new IntersectionObserver(onvisible, {});
        const observerDiv = document.querySelector(".observer");
        if (!observerDiv) {
            console.error("Cannot initialize observer")
            return
        }
        observer.observe(observerDiv);
    });
</script>

<div class="observer"></div>

<style>
    .observer {
        visibility: hidden;
        width: 100%;
        height: 20px;
    }
</style>
