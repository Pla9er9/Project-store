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
	--size: {size};	
	margin: {margin};
	cursor:{cursor};"
/>

<style>
    #avatar {
        width: 85vw;
        max-width: var(--size);
        height: 85vh;
        max-height: var(--size);
        border: solid 1px var(--lightBorder);
        border-radius: 100%;
        background-color: rgba(241, 241, 241, 0);
        background-position: center;
        background-size: cover;
        background-repeat: no-repeat;
    }
</style>
