<script lang="ts">
    import fetchHttp from "$lib/fetchHttp";
    import { alertStore } from "$lib/stores/alertStore";
    import { spaceStore } from "$lib/stores/spaceStore";
    import { tokenStore } from "$lib/stores/tokenStore";
    import { getNameByPath } from "$lib/utils/fileUtils";
    import { get } from "svelte/store";

    export let filepath: string;
    export let content: string;

    let orginal = get(spaceStore).loadedFiles.get(filepath);

    async function saveChanges() {
        const formData = new FormData();
        const lastIndex = filepath.lastIndexOf("/")
        let filename = filepath
        if (lastIndex !== -1) {
            filename = filepath.slice(lastIndex + 1)
        }

        var blob = new Blob([content], { type: "text/plain" });
        var file = new File([blob], filename, { type: "text/plain" });
        formData.append("file", file);

        const res = await fetchHttp(`/project/${get(spaceStore).projectId}/files?path=${encodeURIComponent(filepath)}`, {
            method: "PUT",
            token: get(tokenStore),
            body: formData,
            stringify: false,
            noContentType: true,
        })

        if (!res?.ok) {
            alertStore.update(a => {
                a.color = "red"
                a.message = "Cannot save changes, try later"
                return a
            })
            return
        }

        spaceStore.update((s) => {
            s.loadedFiles.set(filepath, content);
            return s;
        });
        orginal = content;
    }

    function cancelChanges() {
        spaceStore.update((s) => {
            s.editedFiles.set(filepath, orginal ? orginal : "");
            return s;
        });
    }
</script>

{#if content != orginal}
    <div class="fileChange row">
        <p>{getNameByPath(filepath)}</p>
        <button on:click={saveChanges}>
            <img src="/icons/checkmark.svg" alt="" />
        </button>
        <button on:click={cancelChanges}>
            <img src="/icons/cross.svg" alt="" />
        </button>
    </div>
{/if}

<style lang="scss">
    .fileChange {
        width: 95%;
        height: 42px;
        border-bottom: solid 1px var(--lightBorder);
        margin: 8px 0;
        box-sizing: border-box;
        padding: 5px 10px;
        overflow-y: auto;

        p {
            margin: 0;
            font-family: monospace;
            font-size: 12px;
            margin-right: auto;
            overflow-x: auto;
        }

        button {
            width: 32px;
            height: 100%;
            background-color: transparent;
            border: none;
            cursor: pointer;
            margin: 0 1px;
            display: flex;
            transition: 150ms ease-in-out;

            &:hover {
                border-radius: 100%;
                background-color: #6077f728;
            }

            img {
                margin: auto;
                width: 15px;
            }
        }
    }
</style>
