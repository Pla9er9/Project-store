<script lang="ts">
    export let data;

    import { goto } from "$app/navigation";
    import DownloadButton from "$components/project/DownloadButton.svelte";
    import DirectoryView from "$components/project/files/DirectoryView.svelte";
    import Readme from "$components/project/files/Readme.svelte";
    import Invitations from "$components/project/Invitations.svelte";
    import LanguageBar from "$components/project/LanguageBar.svelte";
    import LinkButtons from "$components/project/LinkButtons.svelte";
    import Release from "$components/project/Release.svelte";
    import LoadingIndicator from "$components/LoadingIndicator.svelte";
    import Settings from "$components/project/Settings.svelte";
    import Stats from "$components/project/Stats.svelte";
    import SwitchButton from "$components/project/SwitchButton.svelte";
    import { PUBLIC_API_URL } from "$env/static/public";
    import type { ProjectDTO } from "$lib/models/project/ProjectDTO.js";
    import { onMount } from "svelte";

    const info: ProjectDTO = data.project_data;
    let files: File[] | null = null;

    let switchValue = "Code";
    let invitationsSwitchText = "Invitations";

    async function loadCode() {
        const req = await fetch(
            PUBLIC_API_URL + "/project/" + data.slug + "/files"
        );

        if (req.status == 404) {
            return {
                files: [],
                folders: [],
            };
        }

        if (!req.ok) {
            files = [];
        }
        files = await req.json();
    }

    onMount(async () => await loadCode());
</script>

<svelte:head>
    <title>{info.name}</title>
</svelte:head>

<main>
    <div
        class="row"
        style="justify-content: space-between; height: max-content;margin-top: 40px;"
    >
        <div class="column" style="align-items: flex-start;">
            <h1>{info.name}</h1>
            <p>{info.description}</p>
        </div>
        <DownloadButton link="{PUBLIC_API_URL}/project/{data.slug}/download" />
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
    <LinkButtons {info} projectId={data.slug} />
    <LanguageBar languages={info.languages} />
    <SwitchButton
        values={[
            "Code",
            "Readme",
            "Space",
            invitationsSwitchText,
            "Release",
            "Settings",
        ]}
        bind:currentValue={switchValue}
    />
    {#if switchValue === "Code"}
        {#if !files}
            <div style="max-width: 1000px; width: 90%;display: flex">
                <LoadingIndicator size="30px" margin="30px auto" />
            </div>
        {:else}
            <DirectoryView data={files} slug={data.slug} />
        {/if}
    {:else if switchValue === "Readme"}
        <Readme readme={data.readme} />
    {:else if switchValue === invitationsSwitchText}
        <Invitations projectId={data.slug} />
    {:else if switchValue === "Space"}
        <span>{goto(`${document.URL}/space`)}</span>
    {:else if switchValue === "Release"}
        <Release
            latestRelease={info.latestReleaseVersion}
            projectId={data.slug}
        />
    {:else if switchValue === "Settings"}
        <Settings projectData={info} />
    {/if}
</main>

<style lang="scss">
    main {
        max-width: 1000px;
        width: 90%;
        margin: 0 auto;

        h1 {
            margin-bottom: 10px;
        }

        p {
            margin-top: 0;
            font-family: sans-serif;
            color: rgb(161, 161, 161);
        }
        span {
            color: rgba(220, 220, 220, 0);
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
                background-color: rgba(220, 220, 220, 0.041);
                border-radius: 15px;
                color: gainsboro;
                font-family: monospace;
            }
        }
    }
</style>
