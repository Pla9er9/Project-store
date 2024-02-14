<script lang="ts">
    export let data

    import { goto } from "$app/navigation";
    import Input from "$components/forms/Input.svelte";
    import SubmitButton from "$components/forms/SubmitButton.svelte";
    import { alertStore } from "$lib/stores/alertStore.js";
    import * as validators from "$lib/validators/registerValidators";
    import { onMount } from "svelte";

    let username = "",
        usernameValidated = false,
        password = "",
        passwordValidated = false;

    $: isFormValidated = usernameValidated && passwordValidated;

    async function sendForm() {
        if (!isFormValidated) return;

        if (data.isOauth2) {
            await sendOauth2Request()
            return
        }
        
        let body = JSON.stringify({
            username: username,
            password: password,
        });
        let res = await fetch("http://localhost:5173/login", {
            method: "post",
            body: body,
        });
        if (res.ok) {
            await sleep(150);
            await goto("/");
            location.reload();
        } else {
            alertStore.update(a => {
                a.message = "Wrong username or password"
                a.color = "red"
                return a
            })
        }
    }

    async function sendOauth2Request() {

    }

    function sleep(ms: number) {
        return new Promise((resolve) => setTimeout(resolve, ms));
    }
</script>

<svelte:head>
    <title>Sign in</title>
</svelte:head>

<main class="form">
    <h1>Project store</h1>
    {#if !data.isOauth2}
        <small>Welcome back!</small>
    {:else}
        <small style="font-size: 16px">Continue to application</small>
    {/if}
    <Input
        placeholder="username"
        validator={validators.validateUsername}
        bind:value={username}
        bind:correct={usernameValidated}
    >
        <img src="/icons/user_outline.svg" alt="" />
    </Input>
    <Input
        placeholder="password"
        validator={validators.validatePassword}
        password={true}
        maxlength={50}
        bind:value={password}
        bind:correct={passwordValidated}
    >
        <img src="/icons/lock.svg" alt="" />
    </Input>
    <SubmitButton
        text="Sign in"
        isValid={isFormValidated}
        callback={() => sendForm()}
        iconUrl="/icons/door_open.svg"
    />
</main>

<style lang="scss">
    .form {
        width: 400px;
        height: 450px;
        margin: 80px auto;
        border: solid 1px var(--lightBorder);
        border-radius: 5px;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: start;

        h1 {
            font-size: 40px;
            color: #e5e0f0;
            margin: 70px 0 8px 0;
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
            width: 20px;
        }
    }
</style>
