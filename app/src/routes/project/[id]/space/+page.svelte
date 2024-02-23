<script lang="ts">
    export let data: DirectoryResponse;

    import FilesBar from "$components/project/space/FilesBar.svelte";
    import CodeMirror from "svelte-codemirror-editor";
    import { javascript } from "@codemirror/lang-javascript";
    import { githubDarkInit } from "@uiw/codemirror-theme-github";
    import { spaceStore, type Space } from "$lib/stores/spaceStore";
    import type { DirectoryResponse } from "$lib/models/file/DirectoryResponse";
    import ChangesBar from "$components/project/space/ChangesBar.svelte";

    let space: Space | undefined = undefined;
    let currentFile: string | undefined = "";

    spaceStore.subscribe((value) => {
        space = value;
        currentFile = value.editedFiles.get(value.currentFile);
    });
</script>

<div>
    <FilesBar directories={data.folders} files={data.files} />
    <div id="file" class="column">
        {#if space !== undefined && space.currentFile != ""}
            <p class="row">
                <img src="/icons/file.svg" alt="" />
                {space.currentFile}
            </p>
            <CodeMirror
                bind:value={currentFile}
				on:change={(c) => spaceStore.update(s => {
					s.editedFiles.set(s.currentFile, c.detail)
					return s
				})}
                lang={javascript()}
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
                        width: "calc(100vw - 568px)",
                        height: "calc(100vh - 105px)",
						fontSize: "14px"
                    },
                }}
            />
        {/if}
    </div>
    <ChangesBar />
</div>

<style lang="scss">
    div {
        display: flex;
        align-items: center;
        justify-content: center;

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
                margin: 0 0 20px 0;
				border-bottom: solid 1px rgb(77, 77, 77);
            }
        }
    }
</style>
