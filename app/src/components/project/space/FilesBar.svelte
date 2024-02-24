<script lang="ts">
    export let directories: string[];
    export let files: string[];

    import File from "./File.svelte";
    import BackBtn from "$components/BackBtn.svelte";
    import { goto } from "$app/navigation";

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
</script>

<!-- svelte-ignore a11y-no-static-element-interactions -->
<div id="filesBar">
    <div class="row">
		<BackBtn callback={back} margin="0 8px 0 0" />
		<p>Back to project page</p>
	</div>
    {#each directories as dir}
        <File path={dir} isDirectory={true} on:delete={onDelete}/>
    {/each}
    {#each files as file}
        <File path={file} isDirectory={false} on:delete={onDelete}/>
    {/each}
</div>

<style lang="scss">
    #filesBar {
        width: 400px;
        height: 100vh;
        padding: 20px 15px;
        box-sizing: border-box;
        border-right: solid 1px rgb(77, 77, 77);
        display: flex;
        flex-direction: column;
        align-items: center;
        overflow: auto;

        .row {
            align-self: flex-start;
			margin-bottom: 15px;
		}
    }
</style>
