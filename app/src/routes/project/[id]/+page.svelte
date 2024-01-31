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
    import Settings from "$components/project/Settings.svelte";
    import Stats from "$components/project/Stats.svelte";
    import SwitchButton from "$components/project/SwitchButton.svelte";
    import { PUBLIC_API_URL } from "$env/static/public";
    import type { ProjectDTO } from "$lib/models/project/ProjectDTO.js";

    const info: ProjectDTO = data.data.info;
    const files = data.data.files;

    let switchValue = "Code";

    let invitationsSwitchText = "Invitations";
    const invitations = data.data.invitations;
    if (invitations !== undefined) {
        invitationsSwitchText += ` (${invitations.length})`;
    }
</script>

<main>
    <h1>{info.name}</h1>
    <DownloadButton link="{PUBLIC_API_URL}/project/{data.slug}/download" />
    <p>{info.description}</p>
    <Stats
        owner={info.owner.username}
        isPrivate={info.isPrivate}
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
        <DirectoryView data={files} slug={data.slug} />
    {:else if switchValue === "Readme"}
        <Readme html={data.data.readme} />
    {:else if switchValue === invitationsSwitchText}
        <Invitations {invitations} projectId={data.slug} />
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
            margin-top: 80px;
        }
        p {
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
