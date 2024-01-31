<script lang="ts">
    export let data;

    import { goto } from "$app/navigation";
    import * as validators from "$lib/validators/projectValidators.js";
    import Input from "$components/forms/Input.svelte";
    import SubmitButton from "$components/forms/SubmitButton.svelte";
    import CheckBox from "$components/forms/CheckBox.svelte";
    import TextArea from "$components/forms/TextArea.svelte";
    import FileInput from "$components/forms/FileInput.svelte";
    import fetchHttp from "$lib/fetchHttp.js";
    import Select from "$components/forms/Select.svelte";
    import getLicenses from "$lib/licenses.js";
    import { alertStore } from "$lib/stores/alertStore.js";

    let projectName = "",
        projectNameValid = false,
        isPrivate = false,
        description = "",
        license = "none";

    $: isFormValidated = projectNameValid;

    let filesToSend: FileList;

    async function createNewProject() {
        if (!isFormValidated) {
            return;
        }

        let body = JSON.stringify({
            name: projectName,
            description: description,
            isPrivate: isPrivate,
            license: license,
        });

        const res = await fetchHttp("/project", {
            method: "POST",
            body: body,
            token: data.token,
        });
        if (!res) return;

        alertStore.update((v) => {
            v.color = "blue";
            v.message = "Sending files please wait";
            return v;
        });

        await sendFiles(res.body.id);
        alertStore.update((v) => {
            v.message = "";
            return v;
        });
        await goto("/project/" + res.body.id);
    }

    async function sendFiles(projectId: string) {
        if (filesToSend != null) {
            Object.values(filesToSend).forEach(async (v: File) => {
                var fl: string = v.webkitRelativePath;
                const path = fl.slice(fl.indexOf("/"), fl.length);

                let formData = new FormData();
                formData.append("file", v);

                await fetchHttp(
                    `project/${projectId}/files?path=${path}&cf=true`,
                    {
                        method: "POST",
                        noContentType: true,
                        stringify: false,
                        token: data.token,
                        body: formData,
                    }
                );
            });
        }
    }
</script>

<main class="form">
    <h1>
        Create new Project <br />
        <small>Projects contains files, issues</small><br /><small
            >and more!</small
        >
    </h1>
    <div>
        <Input
            placeholder="Project name"
            validator={validators.validateProjectName}
            maxlength={50}
            bind:value={projectName}
            bind:correct={projectNameValid}
        >
            <img src="icons/pen.svg" alt="" />
        </Input>
        <CheckBox
            label="Private"
            bind:value={isPrivate}
            border="solid 1px var(--lightBorder)"
        >
            <img src="icons/lock.svg" alt="" />
        </CheckBox>
        <TextArea
            width="195px"
            maxlength={140}
            bind:value={description}
            placeholder="Short description - max 140 characters"
        />
        <Select
            text="License"
            bind:value={license}
            options={getLicenses()}
            imgSrc="/icons/license.svg"
        />
        <FileInput bind:value={filesToSend} />
        <SubmitButton
            text="Create"
            isValid={isFormValidated}
            callback={() => createNewProject()}
            iconUrl="icons/rocket_up.svg"
        />
    </div>
</main>

<style lang="scss">
    .form {
        background-color: rgb(14, 14, 14);
        border: solid 1px #ffffff21;
        border-radius: 10px;
        margin: 90px auto;
        width: 450px;
        height: 620px;
        position: absolute;
        padding: 30px 0px;
        left: 0;
        right: 0;
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
        align-items: start;

        h1 {
            width: 100%;
            margin-bottom: 0px;
            text-align: center;
            color: #eeeeee;
            font-family: "Fira sans";
            line-height: 35px;

            small {
                color: rgb(172, 172, 172);
                font-size: 18px;
                line-height: 0px;
            }
        }

        img {
            width: 22px;
        }
    }
</style>
