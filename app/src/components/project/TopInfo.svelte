<script lang="ts">
    import { PUBLIC_API_URL } from "$env/static/public";
    import type { ProjectDTO } from "$lib/models/project/ProjectDTO";
    import DownloadButton from "./DownloadButton.svelte";
    import Stats from "$components/project/Stats.svelte";

    export let info: ProjectDTO;
</script>

<div
    class="row"
    style="justify-content: space-between; height: max-content;margin-top: 40px;"
>
    <div class="column" style="align-items: flex-start;">
        <h1>{info.name}</h1>
        <p>{info.description}</p>
    </div>
    <DownloadButton link="{PUBLIC_API_URL}/project/{info.id}/download" />
</div>
<Stats
    owner={info.owner.username}
    isPrivate={info.private}
    createdDate={info.created}
    license={info.license}
    codeWeight={info.codeSize}
/>
{#if info.tags.length > 0}
    <div class="row tags">
        {#each info.tags as tag}
            <div class="tag">{tag}</div>
        {/each}
    </div>
{/if}

<style lang="scss">
    h1 {
        margin-bottom: 10px;
    }

    p {
        margin-top: 0;
        font-family: sans-serif;
        color: rgb(161 161 161);
    }

    .tags {
        width: 90%;
        max-width: 750px;
        overflow-x: auto;

        .tag {
            width: max-content;
            height: max-content;
            margin: 0 4px;
            padding: 3px 8px;
            border: solid 1px var(--lightBorder);
            background-color: rgba(220 220 220 / 4.1%);
            border-radius: 15px;
            color: gainsboro;
            font-family: monospace;
        }
    }
</style>
