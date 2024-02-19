<script lang="ts">
    import type { IssueDtoSimple } from "$lib/models/issue/IssueDtoSimple";

    export let issue: IssueDtoSimple;
</script>

<a class="issueRow" href="/project/{issue.projectId}/issues/{issue.id}">
    {#if issue.isOpen}
        <div class="circle open" />
    {:else}
        <div class="circle closed" />
    {/if}
    <p>{issue.title}</p>
    <small>{issue.created.slice(0, 10)}</small>
</a>

<style lang="scss">
    .issueRow {
        width: 100%;
        height: 60px;
        display: flex;
        padding: 0 20px;
        box-sizing: border-box;
        align-items: center;
        border: solid 1px var(--lightBorder);
        transition: background-color 100ms;

        &:hover {
            background-color: #ffffff05;
        }

        .circle {
            width: 15px;
            height: 15px;
            margin-right: 20px;
            border-radius: 50%;
        }
        .open {
            background: rgb(136, 252, 3);
            background: radial-gradient(
                circle,
                rgba(136, 252, 3, 1) 0%,
                rgba(39, 126, 0, 1) 100%
            );
        }

        .closed {
            background: rgb(164, 0, 251);
            background: radial-gradient(
                circle,
                rgba(164, 0, 251, 1) 0%,
                rgba(55, 0, 238, 1) 100%
            );
        }

        p {
            font-size: 15px;
            font-family: "Fira sans";
            margin-right: auto;
        }

        small {
            font-family: "Gill Sans", "Gill Sans MT", Calibri, "Trebuchet MS",
                sans-serif;
            min-width: max-content;
            font-size: 12px;
            color: rgb(139, 139, 139);
        }

        &:first-of-type {
            border-top-left-radius: 5px;
            border-top-right-radius: 5px;
        }

        &:last-child {
            border-bottom-left-radius: 5px;
            border-bottom-right-radius: 5px;
        }
    }
</style>
