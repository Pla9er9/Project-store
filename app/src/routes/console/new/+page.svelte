<script>
    import { goto } from "$app/navigation";
    import Input from "$components/forms/Input.svelte";
    import SubmitButton from "$components/forms/SubmitButton.svelte";
    import CheckBox from "$components/forms/CheckBox.svelte";
    import fetchHttp from "$lib/fetchHttp";
    import { get } from "svelte/store";
    import { tokenStore } from "$lib/stores/tokenStore";
    import BackBtn from "$components/BackBtn.svelte";

    let name = ""
    let redirectUrl1 = ""
    let redirectUrl2 = ""
    let isCommercial = false

    async function back() {
        await goto("/console");
    }

    async function submit() {
        const res = await fetchHttp("/dev/application", {
            method: "POST",
            token: get(tokenStore),
            body: JSON.stringify({
                name: name,
                isCommercial: isCommercial,
                allowedRedirectUrls: [redirectUrl1, redirectUrl2]
            })
        })

        if (res?.ok) {
            await goto("/console")
        }
    }

</script>

<main class="column">
    <div class="row" style="margin-bottom: 30px;width: 100%">
        <BackBtn callback={back}/>
        <p style="margin: 0 auto;">New application</p>
    </div>
    <Input placeholder="Name" bind:value={name}>
        <img src="/icons/application.svg" alt="" style="width: 18px;" />
    </Input>
    <Input placeholder="Redirect URL" bind:value={redirectUrl1} >
        <img src="/icons/link.svg" alt="" style="width: 18px;" />
    </Input>
    <Input placeholder="Redirect URL" bind:value={redirectUrl2}>
        <img src="/icons/link.svg" alt="" style="width: 18px;" />
    </Input>
    <CheckBox
        label="Commercial use"
        bind:value={isCommercial}
        border="solid 1px var(--lightBorder)"
        marginLeft="65px"
    /> <br />
    <SubmitButton
        text="Create"
        iconUrl="/icons/checkmark.svg"
        callback={submit}
        isValid={true}
    />
</main>

<style lang="scss">
    main {
        max-width: 500px;
        width: 90%;
        min-height: 480px;
        box-sizing: border-box;
        padding: 30px 20px;
        border: solid 1px var(--lightBorder);
        border-radius: 5px;
        margin: 60px auto;
        background: linear-gradient(
            130deg,
            var(--background) 0%,
            #bd7ef7 3500%
        );

        p {
            width: 230px;
            margin: 0;
            font-family: "Fira sans";
            font-size: 24px;
        }
    }
</style>
