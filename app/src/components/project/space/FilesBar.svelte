<script lang="ts">
    export let directories: string[];
    export let files: string[];

    import File from "./File.svelte";
    import BackBtn from "$components/BackBtn.svelte";
    import { goto } from "$app/navigation";
    import { alertStore } from "$lib/stores/alertStore";
    import { addNewByPath } from "$lib/utils/fileUtils";
    import { spaceStore } from "$lib/stores/spaceStore";

	async function back() {
		const href = location.href
		await goto(href.replace("/space", ""))
	}

    function onDelete(event: CustomEvent) {
        if (event.detail.isDir) {
            directories = directories.filter(value => value !== event.detail.path)
        } else {
            files = files.filter(value => value !== event.detail.path)
        }
    }

    function onNew(event: CustomEvent) {
        if (event.detail.isDir) {
            directories = [...directories, event.detail.path]
        } else {
            files = [...files, event.detail.path]
        }
    }

    async function addNew() {
        const filename = prompt("Enter filename")
        if (!filename) return

        if (files.filter(e => e === filename).length !== 0) {
            alertStore.update(a => {
                a.color = "yellow"
                a.message = "File already exist"
                return a
            })
        }
        const succes = await addNewByPath("", filename)
        if (!succes) {
            alertStore.update(a => {
                a.color = "red"
                a.message = "Could not create new file"
                return a
            })
            return
        }

        files = [...files, filename]
        spaceStore.update(s => {
            s.loadedFiles.set(filename, "")
            s.editedFiles.set(filename, "")
            return s
        })
    }
</script>

<div id="filesBar">
    <div class="row">
		<BackBtn callback={back} margin="0 8px 0 0" />
		<p>Back to project page</p>
	</div>
    {#each directories as dir}
        <File path={dir} isDirectory={true} on:delete={onDelete} on:new={onNew}/>
    {/each}
    {#each files as file}
        <File path={file} isDirectory={false} on:delete={onDelete} on:new={onNew}/>
    {/each}
    <button class="addBtn row" on:click={addNew}>
        <img src="/icons/plus.svg" alt="">
    </button>
</div>

<style lang="scss">
    #filesBar {
        min-width: 300px;
        height: 100vh;
        padding: 20px 15px;
        box-sizing: border-box;
        border-right: solid 1px rgb(77, 77, 77);
        display: flex;
        position: static;
        flex-direction: column;
        align-items: center;
        overflow: auto;

        .row {
            align-self: flex-start;
			margin-bottom: 15px;
		}
    }

    .addBtn {
        width: 50px;
        height: 50px;
        background: inherit;
        border: solid 1px #fff;
        border-radius: 100%;
        position: fixed;
        left: 235px;
        justify-content: center;
        bottom: 0px;
        cursor: pointer;
        transition: box-shadow 150ms ease-in-out;

        &:hover {
            border: solid 2px #fff;
        }

        img {
            width: 28px;
        }
    }
</style>
