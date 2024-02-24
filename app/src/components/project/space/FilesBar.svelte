<script lang="ts">
    export let directories: string[];
    export let files: string[];

    import { onMount } from "svelte";
    import Directory from "./Directory.svelte";
    import File from "./File.svelte";
    import RightClickMenu from "./RightClickMenu.svelte";
    import BackBtn from "$components/BackBtn.svelte";
    import { goto } from "$app/navigation";

    let menuVisible = false;
    let mouseX = 0;
    let mouseY = 0;

    function showMenu(e: MouseEvent) {
        e.preventDefault();
        mouseX = e.pageX;
        mouseY = e.pageY;
        menuVisible = true;
    }

	async function back() {
		const href = location.href
		await goto(href.replace("/space", ""))
	}

    onMount(() => {
        document.onclick = () => (menuVisible = false);
    });
</script>

<!-- svelte-ignore a11y-no-static-element-interactions -->
<div id="filesBar" on:contextmenu={showMenu}>
    <div class="row">
		<BackBtn callback={back} margin="0 8px 0 0" />
		<p>Back to project page</p>
	</div>
    <RightClickMenu bind:mouseX bind:mouseY bind:menuVisible />
    {#each directories as dir}
        <Directory path={dir} />
    {/each}
    {#each files as file}
        <File path={file} />
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
