<script lang="ts">
	import TextArea from '$components/forms/TextArea.svelte';
	import Input from '$components/forms/Input.svelte';
	import SubmitButton from '$components/forms/SubmitButton.svelte';
	import FileInput from '$components/forms/FileInput.svelte';

	let title = '';
	let description = '';
    let fileValue: FileList
</script>

<main class="column">
	<h1>Report error</h1>
	<Input bind:value={title} validator={() => ''} placeholder="Title" width="calc(90% + 30px)">
		<img src="icons/t_letter.svg" alt="" />
	</Input>
	<TextArea placeholder="Description" bind:value={description} width="90%" height="400px" />
	<div class="row" style="width: calc(90% + 40px);margin-top: 10px;justify-content: space-between">
        <FileInput value={fileValue} height="40px" text="Add adicional files" />
		<SubmitButton callback={() => alert()} isValid={true} text="Send">
			<img src="icons/checkmark.svg" alt="" />
		</SubmitButton>
	</div>
</main>

<style lang="scss">
	main {
		width: 100%;
		max-width: 1200px;
		margin: 100px auto;

		h1 {
			width: 100%;
			text-indent: 30px;
		}

		img {
			width: 18px;
		}
	}
</style>
