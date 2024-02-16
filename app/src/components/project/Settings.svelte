<script lang="ts">
	export let projectData: any;

	import * as validators from "$lib/validators/projectValidators.js";
	import Input from "$components/forms/Input.svelte";
	import SubmitButton from "$components/forms/SubmitButton.svelte";
	import CheckBox from "$components/forms/CheckBox.svelte";
	import TextArea from "$components/forms/TextArea.svelte";
	import fetchHttp from "$lib/fetchHttp.js";
	import { tokenStore } from "$lib/stores/tokenStore";
	import { get } from "svelte/store";
	import { goto } from "$app/navigation";
	import Select from "$components/forms/Select.svelte";
	import getLicenses from "$lib/licenses";
	import TagInput from "$components/forms/TagInput.svelte";

	let projectName = projectData.name,
		projectNameValid = true,
		isPrivate = projectData.isPrivate,
		description = projectData.description,
		license = projectData.license ? projectData.license : "none",
		tags = projectData.tags;

	$: isFormValidated = projectNameValid;

	async function editProject() {
		await fetchHttp(`/project/${projectData.id}`, {
			method: "put",
			token: get(tokenStore),
			body: JSON.stringify({
				name: projectName,
				description: description,
				isPrivate: isPrivate,
				license: license,
				tags: tags,
			}),
		});
		location.reload();
	}

	async function deleteProject() {
		await fetchHttp(`/project/${projectData.id}`, {
			method: "delete",
			token: get(tokenStore),
		});
		await goto(`${location.host}/${projectData.owner.username}`);
	}
</script>

<div class="row" style="margin-top: 40px;">
	<div class="column">
		<Input
			placeholder="Project name"
			validator={validators.validateProjectName}
			maxlength={50}
			bind:value={projectName}
			bind:correct={projectNameValid}
		>
			<img src="/icons/pen.svg" alt="" />
		</Input>
		<CheckBox
			label="Private"
			bind:value={isPrivate}
			border="solid 1px var(--lightBorder)"
		>
			<img src="/icons/lock.svg" alt="" />
		</CheckBox>
		<TextArea
			width="195px"
			maxlength={140}
			bind:value={description}
			placeholder="Short description - max 140 characters"
		/>
	</div>
	<div class="column">
		<TagInput bind:tags />
	</div>
	<div class="column">
		<Select
			text="License"
			bind:value={license}
			options={getLicenses()}
			imgSrc="/icons/license.svg"
		/>
		<br />
		<SubmitButton
			text="Confirm"
			isValid={isFormValidated}
			callback={editProject}
			iconUrl="/icons/checkmark.svg"
		/>
		<button id="deleteProject" on:click={deleteProject}>
			<div class="row" style="width: 100%;">
				<img
					src="/icons/delete_danger.svg"
					alt=""
					style="margin-left: 15px;"
				/>
				<p>Delete project</p>
			</div>
		</button>
	</div>
</div>

<style lang="scss">
	img {
		width: 20px;
	}

	#deleteProject {
		width: 245px;
		height: 40px;
		outline: solid 1px var(--danger);
		margin: 10px 0 25px 0;
		cursor: pointer;
		background: inherit;
		border: none;
		border-radius: 10px;
		transition: 250ms ease-in-out;

		&:hover {
			background-color: #e62f2f18;
			outline: none;
		}

		p {
			color: var(--dark-danger);
			font-family: sans-serif;
			margin: 0 auto;
		}
	}
	.column {
		margin: auto 25px;
	}
</style>
