<script lang="ts">
    export let data;

    import * as validators from "$lib/validators/registerValidators.js";
    import Input from "$components/forms/Input.svelte";
    import TextArea from "$components/forms/TextArea.svelte";
    import SubmitButton from "$components/forms/SubmitButton.svelte";
    import Avatar from "$components/Avatar.svelte";
    import fetchHttp from "$lib/fetchHttp.js";
    import { alertStore } from "$lib/stores/alertStore.js";

    let links = data.data.personalLinks;
    let linksLength = 0;
    if (links != null) {
        linksLength = links.length;
    }

    let files: FileList;
    let usernameValidated = true,
        emailValidated = true,
        firstnameValidated = true,
        lastnameValidated = true,
        d = data.data.description,
        description = d ? d : "",
        link1 = linksLength > 0 ? links[0] : "",
        link2 = linksLength > 1 ? links[1] : "",
        link3 = linksLength > 2 ? links[2] : "";

    $: isFormValidated =
        usernameValidated &&
        emailValidated &&
        firstnameValidated &&
        lastnameValidated;

    async function editAccount() {
        const body = {
            username: data.data.username,
            description: description,
            email: data.data.email,
            firstname: data.data.firstname,
            lastname: data.data.lastname,
            personalLinks: [link1, link2, link3],
        };

        const res = await fetch("/settings", {
            method: "post",
            headers: {
                "Content-Type": "application/json;charset=UTF-8",
            },
            body: JSON.stringify(body),
        });

        if (res.ok) {
            location.reload()
        } else {
            alertStore.update(a => {
                a.message = "Could not save settings"
                a.color = "red"
                return a
            })
        }
    }

    async function uploadAvatar() {
        await sleep(300);
        let item = files.item(0);
        if (item === null) {
            return;
        }
        const formData = new FormData();
        formData.append("file", item);
        await fetchHttp("/account/avatar", {
            token: data.token,
            method: "post",
            noContentType: true,
            stringify: false,
            body: formData,
        });
    }

    function sleep(ms: number) {
        return new Promise((resolve) => setTimeout(resolve, ms));
    }

    async function deleteAvatar() {
        await fetchHttp("/account/avatar", {
            token: data.token,
            method: "delete",
            body: undefined,
        });
    }
</script>

<svelte:head>
    <title>Account settings</title>
</svelte:head>

<main class="column" style="align-items: flex-start;">
    <h1 class="settingsHeader">Edit account</h1>
    <div class="row" style="flex-wrap: wrap;max-width: 550px;width: 100vw">
        <div class="column">
            <div class="avatarPanel">
                {#if data.username}
                    <Avatar username={data.username} margin="0 auto 25px auto" />
                {/if}
                <div
                    class="row"
                    style="width: 200px;justify-content:space-around"
                >
                    <input
                        type="file"
                        on:change={uploadAvatar}
                        bind:files
                        accept="image/png, image/jpeg"
                    />
                    <button on:click={deleteAvatar}>delete</button>
                </div>
            </div>
            <Input
                placeholder="username"
                validator={validators.validateUsername}
                bind:value={data.data.username}
                bind:correct={usernameValidated}
            >
                <img src="/icons/user_outline.svg" alt="" />
            </Input>
            <Input
                placeholder="firstname"
                validator={validators.validateFirstname}
                bind:value={data.data.firstname}
                bind:correct={firstnameValidated}
            >
                <img src="/icons/personal_data_outline.svg" alt="" />
            </Input>
            <Input placeholder="Link" validator={() => ""} bind:value={link1}>
                <img src="/icons/link.svg" alt="link" />
            </Input>
            <Input placeholder="Link" validator={() => ""} bind:value={link3}>
                <img src="/icons/link.svg" alt="link" />
            </Input>
        </div>
        <div class="column" style="align-self:flex-end;">
            <TextArea
                bind:value={description}
                placeholder="Description"
                width="195px"
            />
            <Input
                placeholder="email"
                maxlength={320}
                validator={validators.validateEmail}
                bind:value={data.data.email}
                bind:correct={emailValidated}
            >
                <img src="/icons/email_outline.svg" alt="" />
            </Input>
            <Input
                placeholder="lastname"
                validator={validators.validateLastname}
                bind:value={data.data.lastname}
                bind:correct={lastnameValidated}
            >
                <img src="/icons/personal_data_outline.svg" alt="" />
            </Input>
            <Input placeholder="Link" validator={() => ""} bind:value={link2}>
                <img src="/icons/link.svg" alt="link" />
            </Input>
            <SubmitButton
                text="Confirm"
                isValid={isFormValidated}
                callback={() => editAccount()}
                iconUrl="/icons/checkmark.svg"
            />
        </div>
    </div>
</main>

<style lang="scss">
    main {
        img {
            width: 20px;
        }
        
        .row {
            width: 550px;
            justify-content: space-around;
            flex-wrap: wrap;
        }

        .avatarPanel {
            width: 215px;
            margin-left: 12px;
            margin-bottom: 40px;

            input[type="file"] {
                width: 88px;
                background-color: #000;
                border-radius: 5px;

                &::before {
                    width: 88px;
                    height: 30px;
                    margin-top: -5px;
                    border-radius: 5px;
                    outline: solid 1px var(--lightBorder);
                    content: "new";
                    position: absolute;
                    color: #fff;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    background-color: var(--background);
                    cursor: pointer;
                }
            }

            button {
                width: 88px;
                height: 30px;
                background-color: rgb(58 8 8);
                color: #fff;
                border: solid 1px #ffffff1f;
                border-radius: 4px;
                cursor: pointer;

                &:hover {
                    background-color: rgba(246 5 5 / 36.4%);
                }
            }
        }
    }
</style>
