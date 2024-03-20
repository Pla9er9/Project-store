<script lang="ts">
    import { PUBLIC_API_URL } from "$env/static/public";
    import fetchHttp from "$lib/fetchHttp";
    import { tokenStore } from "$lib/stores/tokenStore";
    import { get } from "svelte/store";

    export let id: string;

    async function loadImage() {
        console.log(`${PUBLIC_API_URL}/cdn/images/${id}`)
        const res = await fetch(`${PUBLIC_API_URL}/cdn/images/${id}`, {
            headers: {
                Authorization: "Bearer " + get(tokenStore),
            },
        });
        return res.blob();
    }
</script>

{#await loadImage()}
    <div class="loadingImg"></div>
{:then imgBase64}
    <img
        src={URL.createObjectURL(imgBase64)}
        alt=""
        style="max-width: 180px;"
    />
{/await}

<style>
    .loadingImg {
        width: 220px;
        height: 120px;
        background: repeating-linear-gradient(
            to right,
            rgba(217 217 220 / 13%) 0%,
            rgba(217 217 220 / 28%) 50%,
            rgba(217 217 220 / 13%) 100%
        );
        border-radius: 10px;
        background-size: 200% auto;
        background-position: 0 100%;
        animation: gradient 1.6s infinite;
        animation-fill-mode: forwards;
        animation-timing-function: linear;
    }

    @keyframes gradient {
        0% {
            background-position: 0 0;
        }
        100% {
            background-position: -200% 0;
        }
    }
</style>
