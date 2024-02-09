<script>
    import { goto } from "$app/navigation";
    import Input from "$components/forms/Input.svelte";
    import SubmitButton from "$components/forms/SubmitButton.svelte";
    import CheckBox from "$components/forms/CheckBox.svelte";

    async function back() {
        const sure = confirm("Are you sure you want cancel form");
        if (sure) {
            await goto("/console");
        }
    }
</script>

<main class="column">
    <div class="row" style="margin-bottom: 30px;width: 100%">
        <button class="backBtn" on:click={back}>
            <img src="/icons/btn_back.svg" alt="" />
        </button>
        <p style="margin: 0 auto;">New application</p>
    </div>
    <Input placeholder="Name">
        <img src="/icons/application.svg" alt="" style="width: 18px;" />
    </Input>
    <Input placeholder="Redirect URL">
        <img src="/icons/link.svg" alt="" style="width: 18px;" />
    </Input>
    <Input placeholder="Redirect URL">
        <img src="/icons/link.svg" alt="" style="width: 18px;" />
    </Input>
    <CheckBox label="Commercial use" value={false} border="solid 1px var(--lightBorder)" /> <br>
    <SubmitButton
        text="Create"
        iconUrl="/icons/checkmark.svg"
        callback={() => {}}
        isValid={true}
    />
</main>

<style lang="scss">
    main {
        max-width: 500px;
        width: 100%;
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

        .backBtn {
            width: 40px;
            height: 40px;
            margin-right: 20px;
            border-radius: 100%;
            background: inherit;
            border: none;
            cursor: pointer;
            display: flex;
            align-items: center;
            justify-content: center;

            &:hover {
                background: rgba(128, 128, 128, 0.062);
            }

            img {
                width: 18px;
                margin-left: 6px;
            }
        }
    }
</style>
