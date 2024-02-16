<script lang="ts">
    import Avatar from "$components/Avatar.svelte";
    import type { UserDtoSimple } from "$lib/models/user/UserDtoSimple";

    export let creators: UserDtoSimple[];
</script>

<div class="creators row">
    {#each creators as creator}
        <a href="/{creator.username}">
            <Avatar size="30px" username={creator.username} cursor="pointer" margin="0" />
        </a>
    {/each}
</div>

<style lang="scss">
    .creators {
        width: 100%;
        max-width: 720px;
        height: 48px;
        padding: 0 10px;
        box-sizing: border-box;
        overflow-X: auto;
        border: solid 1px var(--lightBorder);
        border-radius: 35px;

        a {
            margin: 0 10px;
        }
    }
</style>
