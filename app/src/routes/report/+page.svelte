<script lang="ts">
    import TextArea from "$components/forms/TextArea.svelte";
    import Input from "$components/forms/Input.svelte";
    import SubmitButton from "$components/forms/SubmitButton.svelte";
    import fetchHttp from "$lib/fetchHttp";
    import { goto } from "$app/navigation";

    let title = "";
    let description = "";

    async function send() {
        const res = await fetchHttp("/report", {
            method: "post",
            body: {
                title: title,
                description: description,
            },
        });
        if (res?.ok) {
            await goto("/");
        } else {
            alert("Error");
        }
    }
</script>

<main class="column">
    <div class="row" style="width: 100%;">
        <img class="bugIcon" src="/icons/bug.svg" alt="" />
        <h1>Report error</h1>
    </div>
    <Input
        bind:value={title}
        validator={() => (title.length > 0 ? "" : "Title length cannot be 0")}
        placeholder="Title"
        width="calc(90% + 30px)"
    >
        <img src="icons/t_letter.svg" alt="" />
    </Input>
    <TextArea
        placeholder="Description"
        bind:value={description}
        maxlength={100000}
        width="90%"
        height="400px"
    />
    <div class="row btnsDown">
        <SubmitButton
            callback={() => send()}
            isValid={true}
            text="Send"
            iconUrl="icons/checkmark.svg"
        />
    </div>
</main>

<style lang="scss">
    main {
        width: 100%;
        max-width: 1200px;
        margin: 50px auto;

        h1 {
            font-size: 40px;
        }

        .bugIcon {
            width: 45px;
            margin-left: 35px;
            margin-right: 25px;
        }

        img {
            width: 18px;
        }

        .btnsDown {
            width: calc(90% + 40px);
            margin-top: 10px;
        }
    }
</style>
