<script lang="ts">
    import { toastStore, type Toast as toast } from "$lib/stores/toastStore";
    import LoadingIndicator from "./LoadingIndicator.svelte";
	import { fly } from 'svelte/transition';
	import { quintOut } from 'svelte/easing';

    let currToast: toast;
    toastStore.subscribe((t) => {
        currToast = t;
    });

    function closeToast() {
        toastStore.update(t => {
            t.status = "none"
            return t
        })
    }

</script>

{#if currToast.status !== "none"}
    <div
        class="toast row"
        transition:fly={{
            delay: 50,
            duration: 200,
            x: 0,
            y: 400,
            opacity: 0.5,
            easing: quintOut,
        }}
    >
        {#if currToast.status === "loading"}
            <div style="margin-right: 25px;">
                <LoadingIndicator size="18px" />
            </div>
        {/if}
        <div class="column">
            <p>{currToast.header}</p>
            <small>{currToast.description}</small>
        </div>
        <button class="close" on:click={closeToast}>
            <img src="/icons/close.svg" alt="">
        </button>
    </div>
{/if}

<style lang="scss">
    .toast {
        min-width: 290px;
        width: max-content;
        min-height: 70px;
        height: max-content;
        position: fixed;
        background: var(--background);
        bottom: 15px;
        right: 25px;
        padding: 10px 20px;
        padding-right: 5px;
        box-sizing: border-box;
        border: solid 1px var(--lightBorder);
        border-bottom: solid 1px #595858;
        border-radius: 5px;

        &:hover .close {
            display: block;
        }

        .close {
            width: max-content;
            background-color: inherit;
            border: none;
            margin: 0 0 auto auto;
            cursor: pointer;
            display: none;

            img {
                width: 25px;
            }
        }

        p {
            font-size: 13px;
            font-family: "Fira sans";
            margin: 0 auto 5px 2px;
        }

        small {
            font-size: 12px;
            margin: 0 auto 0 2px;
            font-family: sans-serif;
            color: gray;
        }
    }
</style>
