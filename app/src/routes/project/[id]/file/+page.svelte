<script lang="ts">
    import { goto } from "$app/navigation";
    import { page } from "$app/stores";
    import DirectoryView from "$components/project/files/DirectoryView.svelte";
    import File from "$components/project/files/File.svelte";
    import { alertStore } from "$lib/stores/alertStore.js";
    import { deleteByPath } from "$lib/utils/fileUtils.js";

    export let data;

    let isFile: boolean = data.data.folders === undefined ? true : false;
    let path = $page.url.searchParams.get("path");
    if (path === null) {
        goto("/404");
    }

    const len = path ? path.length : 0;
    const getFoldersStructure = () => {
        let current = "";
        const folderStructure: string[] = [];

        for (let i = 0; i < len; i++) {
            if (path![i] === "/" && current !== "") {
                folderStructure.push(current);
                current = "";
            } else {
                current += path![i];
            }
        }
        folderStructure.push(current);

        return folderStructure;
    };
    const folderStructure = getFoldersStructure();

    function getFileExtension(filename: string | null): string {
        if (!filename) return "";
        const dotIndex = filename.lastIndexOf(".");
        return dotIndex >= 0
            ? filename.substring(dotIndex + 1).toLowerCase()
            : "";
    }

    async function deleteHandler() {
        if (!path) return;
        const succes = await deleteByPath(path);
        if (!succes) {
            alertStore.update((a) => {
                a.message = "Error occurred, try again later";
                a.color = "red";
                return a;
            });
        } else {
            const i = location.href.indexOf("file");
            location.href = location.href.slice(0, i);
        }
        const i = location.href.indexOf("file");
        location.href = location.href.slice(0, i);
    }

    async function editHandler() {
        const i = location.href.indexOf("file");
        await goto(location.href.slice(0, i) + "space?path=" + path);
    }
</script>

<main class="column">
    <div class="row">
        <a href=".">..</a>
        {#each folderStructure as folder, i (i)}
            <img src="/icons/arrow_right.svg" alt="" />
            <a
                href="?path={folderStructure.slice(0, i + 1).join('/')}"
                data-sveltekit-reload>{folder.replaceAll("/", "")}</a
            >
        {/each}
        {#if isFile}
            <button class="actionBtn" on:click={editHandler}>
                <img src="/icons/edit.svg" alt="" />
            </button>
        {/if}
        <button class="actionBtn" on:click={deleteHandler}>
            <img src="/icons/delete.svg" alt="" />
        </button>
    </div>
    {#if isFile}
        <File code={data.data} lang={getFileExtension(path)} />
    {:else}
        <DirectoryView data={data.data} slug={data.slug} />
    {/if}
</main>

<style lang="scss">
    main {
        width: 90%;
        max-width: 900px;
        margin: 60px auto;

        .row {
            width: 100%;
            height: 30px;
            margin: 15px auto 15px 15px;

            img {
                width: 20px;
                margin: 0 3px;
            }

            a {
                font-family: "Fira sans";
                font-size: 15px;
                color: #dfdcdc;
                transition: text-decoration 150ms;

                &:first-child {
                    margin: 0px;
                }

                &:hover {
                    text-decoration: underline;
                }

                &:last-of-type {
                    color: rgb(0, 110, 255);
                }
            }

            .actionBtn {
                min-width: 32px;
                margin: 0 4px;
                height: 32px;
                padding: 0;
                background-color: transparent;
                border: solid 1px var(--lightBorder);
                border-radius: 5px;
                cursor: pointer;
                transition: border 250ms ease-in-out;
                display: flex;

                &:hover {
                    border: solid 1px #8a8a8a;
                }

                &:first-of-type {
                    margin-left: auto;
                }

                &:last-of-type {
                    margin-right: 15px;
                }

                &:last-of-type:hover {
                    border: solid 1px var(--dark-danger);
                }

                img {
                    margin: auto;
                    width: 15px;
                }
            }
        }
    }
</style>
