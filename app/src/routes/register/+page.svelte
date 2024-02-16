<script lang="ts">
    import { goto } from "$app/navigation";
    import Input from "$components/forms/Input.svelte";
    import SubmitButton from "$components/forms/SubmitButton.svelte";
    import fetchHttp from "$lib/fetchHttp";
    import { alertStore } from "$lib/stores/alertStore";
    import * as validators from "$lib/validators/registerValidators";

    let username = "",
        usernameValidated = false,
        email = "",
        emailValidated = false,
        password = "",
        passwordValidated = false,
        firstname = "",
        firstnameValidated = false,
        lastname = "",
        lastnameValidated = false;

    $: isFormValid =
        usernameValidated &&
        emailValidated &&
        passwordValidated &&
        firstnameValidated &&
        lastnameValidated;

    async function sendForm() {
        if (!isFormValid) return;
        let res = await fetch("/register", {
            method: "post",
            body: JSON.stringify({
                username: username,
                email: email,
                password: password,
                firstname: firstname,
                lastname: lastname,
            })
        })
        if (res?.status === 200) {
            await goto("/");
            location.reload();
        } else {
            alertStore.update(a => {
                a.color ="red"
                a.message = "Unabled to create account"
                return a
            })
        }
    }
</script>

<svelte:head>
    <title>Sign up</title>
</svelte:head>

<main>
    <h1>Project store</h1>
    <small>Registration</small>
    <div class="form">
        <Input
            placeholder="username"
            validator={validators.validateUsername}
            bind:value={username}
            bind:correct={usernameValidated}
        >
            <img src="/icons/user_outline.svg" alt="" />
        </Input>
        <Input
            placeholder="email"
            maxlength={320}
            validator={validators.validateEmail}
            bind:value={email}
            bind:correct={emailValidated}
        >
            <img src="/icons/email_outline.svg" alt="" />
        </Input>
        <Input
            placeholder="password"
            validator={validators.validatePassword}
            bind:value={password}
            bind:correct={passwordValidated}
            maxlength={50}
            password={true}
        >
            <img src="/icons/password_outline.svg" alt="" />
        </Input>
        <Input
            placeholder="firstname"
            validator={validators.validateFirstname}
            bind:value={firstname}
            bind:correct={firstnameValidated}
        >
            <img src="/icons/personal_data_outline.svg" alt="" />
        </Input>
        <Input
            placeholder="lastname"
            validator={validators.validateLastname}
            bind:value={lastname}
            bind:correct={lastnameValidated}
        >
            <img src="/icons/personal_data_outline.svg" alt="" />
        </Input>
        <SubmitButton
            text="Register"
            isValid={isFormValid}
            callback={async () => await sendForm()}
			iconUrl="/icons/checkmark.svg"
        />
    </div>
</main>

<style lang="scss">
    main {
        max-width: 700px;
		min-height: 450px;
        width: 100%;
        margin: 80px auto;
        display: flex;
        flex-direction: column;
        align-items: center;
		justify-content: center;
        border: solid 1px var(--lightBorder);
		border-radius: 5px;

        h1 {
            font-size: 40px;
            color: #e5e0f0;
            margin: 0px 0 8px 0;
            font-family: "Inter";
            font-weight: 100;
            text-align: center;
        }

        small {
            font-size: 22px;
            color: rgb(154, 153, 153);
            font-family: 'Fira sans';
            margin-bottom: 40px;
        }

        img {
            margin: 0;
            width: 150px;
            height: 150px;
        }

        .form {
            max-width: 600px;
            width: 95%;
            display: flex;
            justify-content: space-around;
            flex-wrap: wrap;
            margin: 0 auto;

            img {
                width: 20px;
            }
        }
    }
</style>
