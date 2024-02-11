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

<svelte:head>
    <title>Report error</title>
</svelte:head>

<main class="column" style="align-items: start;">
    <div class="row" style="width: 100%;">
        <img class="bugIcon" src="/icons/bug.svg" alt="" />
        <h1>Report error</h1>
    </div>
    <Input
        bind:value={title}
        validator={() => (title.length > 0 ? "" : "Title length cannot be 0")}
        placeholder="Title"
        width="400px"
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
    <SubmitButton
        callback={() => send()}
        isValid={true}
        text="Send"
        iconUrl="icons/checkmark.svg"
    />
</main>

<style lang="scss">
    main {
        width: 100%;
        max-width: 1200px;
        padding: 0 30px;
        box-sizing: border-box;
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
    }

    @media screen and (max-width: 476px) {
		main {
            align-items: center !important;
        
            .row img {
                margin-left: 0;
            }
        }
	}
</style>
