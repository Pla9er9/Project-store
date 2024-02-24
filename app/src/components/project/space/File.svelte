<script lang="ts">
    import { PUBLIC_API_URL } from "$env/static/public";
    import fetchHttp from "$lib/fetchHttp";
    import { alertStore } from "$lib/stores/alertStore";
    import { spaceStore } from "$lib/stores/spaceStore";
    import { tokenStore } from "$lib/stores/tokenStore";
    import { get } from "svelte/store";
    import { blur } from "svelte/transition";
    import { createEventDispatcher } from 'svelte'

    export let path: string;
    export let isDirectory: boolean;

    let directories: string[] = [];
    let files: string[] = [];

    const fileName = getNameByPath(path);
    const dispatch = createEventDispatcher();
    let loaded = false;
    let expand = false;
    let menuOpened = false;

    let count = 0;
    for (let i = 0; i < path.length; i++) if (path[i] === "/") count++;

    function getNameByPath(path: string): string {
        let res = "";
        for (let i = path.length - 1; i >= 0; i--) {
            if (path[i] == "/") {
                return res;
            } else {
                res = path[i] + res;
            }
        }
        return res;
    }

    async function loadFile() {
        const res = await fetch(`${PUBLIC_API_URL}/project/
            ${get(spaceStore).projectId}/files?path=${path}`);
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
            v.currentFile = fileName;
            return v;
        });
    }

    async function deleteByPath() {
        const res = await fetchHttp(
            `/project/${get(spaceStore).projectId}/files?path=${path}`,
            {
                method: "DELETE",
                token: get(tokenStore),
            }
        );
        if (!res?.ok) {
            alertStore.update((a) => {
                a.color = "red"
                a.message = "Could not delete file";
                return a;
            });
        }

        spaceStore.update((s) => {
            if (s.currentFile === path) {
                s.currentFile = "";
            }
            s.editedFiles.delete(path);
            s.loadedFiles.delete(path);
            return s;
        });

        dispatch("delete", {
            path: path,
            isDir: isDirectory
        })
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
            }/files?path=${path}`
        );
        const body = await res.json();
        if (body.folders !== undefined) {
            directories = [...body.folders];
            files = [...body.files];
        }

        loaded = true;
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
            <img src="/icons/{menuOpened ? 'cross_white' : 'three_dots'}.svg" alt="" />
        </button>
    </button>
    {#if menuOpened}
        <div class="row options" transition:blur={{ amount: 10 }}>
            <button>Rename</button>
            <button on:click={deleteByPath}>Delete</button>
        </div>
    {/if}
    {#if expand}
        {#each directories as dir}
            <svelte:self path="{path}/{dir}" isDirectory={true} />
        {/each}
        {#each files as file}
            <svelte:self path="{path}/{file}" isDirectory={false} />
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

        &:hover {
            background-color: #ffffff21;

            .more {
                display: flex !important;
            }
        }

        img {
            width: 18px;
            height: 18px;
            margin-left: var(--marginL);
            margin-right: 12px;
        }
    }

    .options {
        border-bottom: solid 1px var(--lightBorder);
        border-radius: 5px;

        button {
            justify-content: center;
            color: #fff;
            padding: 0; 

            &:last-of-type:hover {
                background-color: rgba(253, 67, 67, 0.178);
            }
        }
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
        background-color: #ffffff00;

        img {
            width: 16px;
            height: 16px;
            margin: 0;
        }
    }
</style>
