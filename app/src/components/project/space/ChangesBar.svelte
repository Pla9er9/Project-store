<script lang="ts">
    import { spaceStore } from "$lib/stores/spaceStore";
    import FileChange from "./FileChange.svelte";

    let editedFiles = new Map<string, string>();
    spaceStore.subscribe((s) => {
        editedFiles = s.editedFiles;
    });
</script>

<div id="changesBar" class="column">
    <p>Changes</p>
    {#each editedFiles as editedFile}
        <FileChange filepath={editedFile[0]} content={editedFile[1]} />
    {/each}
</div>

<style lang="scss">
    #changesBar {
        width: 400px;
        height: 100vh;
        border-left: solid 1px rgb(77 77 77);
        padding: 20px 15px;
        box-sizing: border-box;

        p {
            align-self: flex-start;
            margin: 5px 15px 20px;
        }
    }
</style>
