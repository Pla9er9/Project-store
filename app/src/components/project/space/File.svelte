<script lang="ts">
    import { PUBLIC_API_URL } from "$env/static/public";
    import { spaceStore } from "$lib/stores/spaceStore";
    import { get } from "svelte/store";
    import { blur } from "svelte/transition";
    import {
        addNewByPath,
        changeName,
        deleteByPath,
        getNameByPath,
    } from "$lib/utils/fileUtils";
    import { createEventDispatcher } from "svelte";
    import { alertStore } from "$lib/stores/alertStore";

    export let path: string;
    export let isDirectory: boolean;

    const dispatch = createEventDispatcher();
    let directories: string[] = [];
    let files: string[] = [];

    let loaded = false;
    let expand = false;
    let menuOpened = false;

    let count = 0;
    for (let i = 0; i < path.length; i++) if (path[i] === "/") count++;

    async function loadFile() {
        const res = await fetch(`${PUBLIC_API_URL}/project/
            ${get(spaceStore).projectId}/files?path=${encodeURIComponent(path)}`);
        if (!res.ok) return;
        const file = await res.text();
        spaceStore.update((v) => {
            v.loadedFiles.set(path, file);
            v.editedFiles.set(path, file);
            return v;
        });
        loaded = true;
    }

    async function setFileToEdit() {
        if (!loaded) {
            await loadFile();
        }
        spaceStore.update((v) => {
            v.currentFile = path;
            return v;
        });
    }

    function onNew(event: CustomEvent) {
        menuOpened = false;
        if (event.detail.isDir) {
            directories = [...directories, event.detail.path];
        } else {
            files = [...files, event.detail.path];
        }
    }

    function onDelete(event: CustomEvent) {
        menuOpened = false;
        if (event.detail.isDir) {
            directories = directories.filter(
                (value) => path + "/" + value !== event.detail.path
            );
        } else {
            files = files.filter(
                (value) => path + "/" + value !== event.detail.path
            );
        }
    }

    async function expandFolder() {
        expand = !expand;
        if (!expand) return;
        await loadDirectory();
        loaded = true;
    }

    async function loadDirectory() {
        if (loaded) {
            return;
        }
        const res = await fetch(
            `${PUBLIC_API_URL}/project/${
                get(spaceStore).projectId
            }/files?path=${encodeURIComponent(path)}`
        );
        const body = await res.json();
        if (body.folders !== undefined) {
            directories = [...body.folders];
            files = [...body.files];
        }

        loaded = true;
    }

    async function changeFilename() {
        const name = getNameByPath(path);
        const newName = prompt(`Enter new filename for - ${name}`);
        if (!newName) return;

        const changed = await changeName(newName, path);
        if (changed) {
            dispatch("delete", {
                path: path,
                isDir: isDirectory,
            });
            path = newName;
            dispatch("new", {
                path: path,
                isDir: isDirectory,
            });
        }
    }

    async function addNew() {
        const filename = prompt("Enter filename");
        if (!filename) return;

        if (files.filter((e) => e === filename).length !== 0) {
            alertStore.update((a) => {
                a.color = "yellow";
                a.message = "File already exist";
                return a;
            });
        }
        const succes = await addNewByPath("", path + "/" + filename);
        if (!succes) {
            alertStore.update((a) => {
                a.color = "red";
                a.message = "Could not create new file";
                return a;
            });
            return;
        }

        files = [...files, filename];
        spaceStore.update((s) => {
            s.loadedFiles.set(path + "/" + filename, "");
            s.editedFiles.set(path + "/" + filename, "");
            return s;
        });
    }

    function addNewDirectory() {
        const dirname = prompt("Enter directory name");
        if (!dirname) return;

        if (directories.filter((e) => e === dirname).length !== 0) {
            alertStore.update((a) => {
                a.color = "yellow";
                a.message = "Directory already exist";
                return a;
            });
        }

        directories = [...directories, dirname];
    }

    async function deleteFile() {
        const deleted = await deleteByPath(path);
        if (deleted) {
            dispatch("delete", {
                path: path,
                isDir: isDirectory,
            });
        }
    }
</script>

<div class="column">
    <button
        style="--marginL: {6 + count * 10}px"
        on:click={isDirectory ? expandFolder : setFileToEdit}
    >
        {#if isDirectory}
            <img src="/icons/dir{expand ? '_expand' : ''}.svg" alt="" />
        {:else}
            <img src="/icons/file.svg" alt="" />
        {/if}
        <p>{getNameByPath(path)}</p>
        <button
            class="more"
            on:click={() => (menuOpened = !menuOpened)}
            style="display: none;"
        >
            <img
                src="/icons/{menuOpened ? 'cross_white' : 'three_dots'}.svg"
                alt=""
            />
        </button>
    </button>
    {#if menuOpened}
        <div class="row options" transition:blur={{ amount: 10 }}>
            {#if isDirectory}
                <button on:click={addNew}>New file</button>
                <button on:click={addNewDirectory}>New Directory</button> <br />
            {/if}
            <button on:click={changeFilename}>Rename</button>
            <button on:click={deleteFile}>Delete</button>
        </div>
    {/if}
    {#if expand}
        {#each directories as dir}
            <svelte:self
                path="{path}/{dir}"
                isDirectory={true}
                on:new={onNew}
                on:delete={onDelete}
            />
        {/each}
        {#each files as file}
            <svelte:self
                path="{path}/{file}"
                isDirectory={false}
                on:new={onNew}
                on:delete={onDelete}
            />
        {/each}
    {/if}
</div>

<style lang="scss">
    div {
        width: 100%;
    }

    button {
        width: calc(100% - 10px);
        height: 30px;
        border-radius: 5px;
        margin: 4px 0;
        background: inherit;
        display: flex;
        align-items: center;
        border: none;
        cursor: pointer;
        transition: background-color 200ms;
        padding: 1px 0;
        padding-left: 6px;

        p {
            color: #fff;
            font-size: 15px;
            font-family: sans-serif;
        }

        img {
            width: 18px;
            height: 18px;
            margin-left: var(--marginL);
            margin-right: 12px;
        }

        .more {
            width: 30px;
            height: 28px;
            display: none;
            align-items: center;
            justify-content: center;
            margin-left: auto;
            padding: 0;
            border-top-left-radius: 0;
            border-bottom-left-radius: 0;
            background-color: #fff0;

            img {
                width: 16px;
                height: 16px;
                margin: 0;
            }
        }

        &:hover {
            background-color: #ffffff21;

            .more {
                display: flex !important;
            }
        }
    }

    .options {
        border-bottom: solid 1px var(--lightBorder);
        border-radius: 5px;
        overflow-x: auto;

        button {
            min-width: 50%;
            justify-content: center;
            color: #fff;
            padding: 0;

            &:last-of-type:hover {
                background-color: rgba(253 67 67 / 17.8%);
            }
        }
    }
</style>
