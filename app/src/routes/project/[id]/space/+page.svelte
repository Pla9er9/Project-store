<script lang="ts">
    export let data: DirectoryResponse;

    import FilesBar from "$components/project/space/FilesBar.svelte";
    import CodeMirror from "svelte-codemirror-editor";
    import { javascript } from "@codemirror/lang-javascript";
    import { githubDarkInit } from "@uiw/codemirror-theme-github";
    import { spaceStore, type Space } from "$lib/stores/spaceStore";
    import type { DirectoryResponse } from "$lib/models/file/DirectoryResponse";
    import ChangesBar from "$components/project/space/ChangesBar.svelte";
    import { onMount } from "svelte";
    import { beforeNavigate } from "$app/navigation";
    import { page } from "$app/stores";
    import { getProjectId } from "$lib/utils/fileUtils";
    import fetchHttp from "$lib/fetchHttp";
    import { alertStore } from "$lib/stores/alertStore";

    let space: Space | undefined = undefined;
    let currentFile: string | undefined = "";
    const path = $page.url.searchParams.get("path");

    spaceStore.subscribe((value) => {
        space = value;
        currentFile = value.editedFiles.get(value.currentFile);
    });

    function closeCurrentFile() {
        spaceStore.update((s) => {
            s.currentFile = "";
            return s;
        });
    }

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

    onMount(async () => {
        const body = document.querySelector("body");
        if (body) body.style.overflowX = "auto";
        await loadFile();
    });

    async function loadFile() {
        if (!path) return;

        const res = await fetchHttp(
            `/project/${getProjectId()}/files?path=${encodeURIComponent(path)}`,
            {}
        );
        if (!res.ok) {
            if (res.status !== 404) {
                alertStore.update((a) => {
                    a.message = "Couldnt load file";
                    a.color = "red";
                    return a;
                });
            }
            return;
        }
        if (res.body.folders) return;

        spaceStore.update((v) => {
            v.loadedFiles.set(path, res.body);
            v.editedFiles.set(path, res.body);
            v.currentFile = path;
            return v;
        });
    }

    beforeNavigate(() => {
        const body = document.querySelector("body");
        if (body) body.style.overflowX = "hidden";
        $page.url.searchParams.delete("path");
    });
</script>

<main class="row">
    <FilesBar directories={data.folders} files={data.files} />
    <div id="file" class="column">
        {#if space !== undefined && space.currentFile != ""}
            <p class="row headline">
                <img src="/icons/file.svg" alt="" />
                <span>{getNameByPath(space.currentFile)}</span>
                <button class="closeBtn" on:click={closeCurrentFile}>
                    <img src="/icons/cross_white.svg" alt="" />
                </button>
            </p>
            <CodeMirror
                bind:value={currentFile}
                on:change={(c) =>
                    spaceStore.update((s) => {
                        s.editedFiles.set(s.currentFile, c.detail);
                        return s;
                    })}
                lang={javascript({ jsx: true })}
                theme={githubDarkInit({
                    settings: {
                        gutterBackground: "#212121",
                        background: "#0c0b0b",
                        selection: "rgb(50, 105, 168, 30%)",
                        lineHighlight: "rgb(211, 225, 242, 5%)",
                    },
                })}
                styles={{
                    "&": {
                        minWidth: "490px",
                        width: "calc(100vw - 568px)",
                        height: "calc(100vh - 105px)",
                        fontSize: "14px",
                    },
                }}
            />
        {:else}
            <div class="column" id="desk">
                <img src="/icons/desk_.svg" alt="" />
                <p>Edit your project <br /> files here</p>
            </div>
        {/if}
    </div>
    <ChangesBar />
</main>

<style lang="scss">
    main {
        min-width: 1000px;
        // background: blue;
        min-height: 100vh;

        #file {
            width: 100%;
            height: 100vh;
            justify-content: start;

            img {
                width: 22px;
                height: 22px;
                margin-left: 15px;
                margin-right: 8px;
            }

            p {
                font-family: monospace;
                font-size: 18px;
                width: 100%;
                height: 60px;
                margin: 0 0 20px;
                border-bottom: solid 1px rgb(77 77 77);
            }

            .closeBtn {
                width: 30px;
                display: flex;
                justify-content: center;
                background-color: inherit;
                border: none;
                cursor: pointer;
                margin: 0 15px 0 auto;

                img {
                    margin: 0;
                }
            }

            #desk {
                margin: 260px auto auto;

                p {
                    border: none;
                    font-family: "Fira sans", sans-serif;
                    text-align: center;
                    color: rgb(204 204 204);
                    line-height: 28px;
                }

                img {
                    width: 160px;
                    height: 160px;
                }
            }
        }

        span {
            font-size: 16px;
        }
    }
</style>
