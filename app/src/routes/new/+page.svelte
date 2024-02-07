<script lang="ts">
    export let data;

    import * as validators from "$lib/validators/projectValidators.js";
    import Input from "$components/forms/Input.svelte";
    import SubmitButton from "$components/forms/SubmitButton.svelte";
    import CheckBox from "$components/forms/CheckBox.svelte";
    import TextArea from "$components/forms/TextArea.svelte";
    import FileInput from "$components/forms/FileInput.svelte";
    import Select from "$components/forms/Select.svelte";
    import getLicenses from "$lib/licenses.js";
    import { toastStore } from "$lib/stores/toastStore.js";
    import fetchHttp from "$lib/fetchHttp.js";
    import { goto } from "$app/navigation";

    let projectName = "",
        projectNameValid = false,
        isPrivate = false,
        description = "",
        license = "none";

    $: isFormValidated = projectNameValid;

    let filesToSend: FileList;
    let filesSent = 0;
    let totalFiles = 0;

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

        await sendFiles(res.body.id);
        await goto("/project/" + res.body.id);
    }

    async function sendFiles(projectId: string) {
        if (filesToSend != null) {
            const values = Object.values(filesToSend)
            totalFiles = values.length;

            for (let i = 0; i < totalFiles; i++) {
                let v = values[i]
                var fl: string = v.webkitRelativePath;
                const path = fl.slice(fl.indexOf("/"), fl.length);

                let formData = new FormData();
                formData.append("file", v);

                const res = await fetchHttp(
                    `project/${projectId}/files?path=${path}&cf=true`,
                    {
                        method: "POST",
                        noContentType: true,
                        stringify: false,
                        token: data.token,
                        body: formData,
                    },
                );
                if (res?.status !== 200) {
                    break
                }
                toastStore.update((t) => {
                    filesSent += 1;
                    t.status = "loading";
                    t.header = "Uploading project files";
                    t.description = `${filesSent}/${totalFiles}`;
                    return t;
                });
            }
        }
    }
</script>

<main class="form">
    <h1>
        Create new Project <br />
        <small>Projects contains files, issues and more!</small>
    </h1>
    <div class="row">
        <div class="column" style="align-items: start;">
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
            <Select
                text="License"
                bind:value={license}
                options={getLicenses()}
                imgSrc="/icons/license.svg"
            />
            <FileInput bind:value={filesToSend} />
        </div>
        <div class="column">
            <TextArea
                width="195px"
                maxlength={140}
                bind:value={description}
                placeholder="Short description - max 140 characters"
            />
            <SubmitButton
                text="Create"
                isValid={isFormValidated}
                callback={createNewProject}
                iconUrl="icons/rocket_up.svg"
            />
        </div>
    </div>
</main>

<style lang="scss">
    .form {
        border: solid 1px #ffffff21;
        border-radius: 5px;
        margin: 60px auto;
        width: 100%;
        max-width: 720px;
        min-height: 400px;
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
            line-height: 32px;

            small {
                color: rgb(172, 172, 172);
                font-size: 14px;
                line-height: 0px;
            }
        }

        .column {
            height: max-content;
            min-height: 240px;
            justify-content: space-between;
            margin: 0 15px;
        }

        img {
            width: 20px;
        }
    }
</style>
