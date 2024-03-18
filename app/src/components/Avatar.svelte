<script lang="ts">
    import { PUBLIC_API_URL } from "$env/static/public";

    export let username: string;
    export let size = "100px";
    export let margin = "30px 0px";
    export let cursor = "auto";

    let url = `${PUBLIC_API_URL}/user/${username}/avatar`;
</script>

<div
    id="avatar"
    style="
	background-image: url('{url}');
	margin: {margin};
	cursor:{cursor};
    --size: {size};"
/>

<style>
    #avatar {
        width: 85vw;
        max-width: var(--size);
        height: 85vh;
        max-height: var(--size);
        border: solid 1px var(--lightBorder);
        border-radius: 100%;
        background-color: transparent;
        background-position: center;
        background-size: cover;
        background-repeat: no-repeat;
    }
</style>
