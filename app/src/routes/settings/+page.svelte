<script lang="ts">
    export let data;
    import * as validators from "$lib/validators/registerValidators.js";
    import Input from "$components/forms/Input.svelte";
    import TextArea from "$components/forms/TextArea.svelte";
    import SubmitButton from "$components/forms/SubmitButton.svelte";
    import { PUBLIC_API_URL } from "$env/static/public";
    import { goto } from "$app/navigation";
    import Avatar from "$components/Avatar.svelte";
    import fetchHttp from "$lib/fetchHttp.js";

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

        const res = await fetch(PUBLIC_API_URL + "/account", {
            method: "put",
            headers: {
                Authorization: "Bearer " + data.token,
                "Content-Type": "application/json;charset=UTF-8",
            },
            body: JSON.stringify(body),
        });
        if (res.ok) {
            goto("/" + data.username);
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
            body: undefined
        });
    }
</script>

<h1>Edit account</h1>
<div class="wrapper">
    <div>
        <div class="avatarPanel">
            <Avatar username="{data.username}" />
            <div style="width: 180px;">
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
            placeholder="email"
            maxlength={320}
            validator={validators.validateEmail}
            bind:value={data.data.email}
            bind:correct={emailValidated}
        >
            <img src="/icons/email_outline.svg" alt="" />
        </Input>
        <Input
            placeholder="firstname"
            validator={validators.validateFirstname}
            bind:value={data.data.firstname}
            bind:correct={firstnameValidated}
        >
            <img src="/icons/personal_data_outline.svg" alt="" />
        </Input>
        <Input
            placeholder="lastname"
            validator={validators.validateLastname}
            bind:value={data.data.lastname}
            bind:correct={lastnameValidated}
        >
            <img src="/icons/personal_data_outline.svg" alt="" />
        </Input>
    </div>
    <div style="align-self: center;margin-top:160px">
        <TextArea bind:value={description} placeholder="Description" />
    </div>
    <div style="align-self: flex-end;">
        <Input placeholder="Link" validator={(s) => ""} bind:value={link1}>
            <img src="/icons/link.svg" alt="link" />
        </Input>
        <Input placeholder="Link" validator={(s) => ""} bind:value={link2}>
            <img src="/icons/link.svg" alt="link" />
        </Input>
        <Input placeholder="Link" validator={(s) => ""} bind:value={link3}>
            <img src="/icons/link.svg" alt="link" />
        </Input> <br />
        <SubmitButton
            text="Confirm"
            isValid={isFormValidated}
            callback={() => editAccount()}
            iconUrl="/icons/checkmark.svg"
        />
    </div>
</div>

<style lang="scss">
	h1 {
		font-family: 'Inter', sans-serif;
	}
    img {
        width: 22px;
    }
    .wrapper {
        max-width: 650px;
        width: 100vw;
        display: flex;
        flex-wrap: wrap;
        align-items: center;
    }
    .avatarPanel {
        width: 215px;
        margin-left: 12px;
        margin-bottom: 40px;
        display: flex;
        flex-direction: column;
        align-items: center;

        input[type="file"] {
            width: 88px;
            background-color: #000;

            &::before {
                width: 88px;
                height: 21px;
                margin-top: 0px;
                border-radius: 3px;
                outline: solid 1px #0d638bc2;
                content: "new";
                position: absolute;
                color: #ffffff;
                display: flex;
                justify-content: center;
                align-items: center;
                background-color: rgb(3, 31, 44);
                cursor: pointer;
            }
        }
        button {
            width: 88px;
            height: 24px;
            background-color: rgb(58, 8, 8);
            color: #fff;
            border: solid 1px #ffffff1f;
            border-radius: 4px;
            cursor: pointer;
        }
    }
</style>
