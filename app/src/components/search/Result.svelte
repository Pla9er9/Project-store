<script lang="ts">
    import Avatar from "$components/Avatar.svelte";

    export let data;
    export let type: "user" | "project" | "mixed";
    export let wide: boolean = true;

    if (data.mainLanguage === null) {
        data.mainLanguage = "No programing language";
    }
</script>

<div class="result row">
    {#if type === "user"}
        <a href="/{data.username}">
            <Avatar
                username={data.username}
                size="42px"
                margin="0 20px 0 0"
                cursor="pointer"
            />
        </a>
        <div class="column" style="align-items: flex-start;">
            <a href="/{data.username}">{data.username}</a>
            <small style="margin-left: 0;"
                >{data.firstname} {data.lastname}</small
            >
        </div>
        <img src="/icons/project.svg" alt="" style="margin: 0 8px 0 auto" />
        <p style="margin-right: auto;">{data.projectCount} Projects</p>
        <img src="/icons/hearth.svg" alt="" style="margin-right: 8px;" />
        <p>{data.followers} Followers</p>
    {:else if type === "project"}
        <div class="row" style="width: 300px;">
            <a href="/{data.owner.username}">
                <Avatar
                    username={data.owner.username}
                    size="42px"
                    margin="0 20px 0 0"
                    cursor="pointer"
                />
            </a>
            <a href="/project/{data.id}">{data.name}</a>
            {#if wide}
                <small>{data.created.slice(0, 10)}</small>
            {/if}
        </div>
        {#if wide}
            <small style="margin: 0 0 0 auto">{data.mainLanguage}</small>
            <div class="languageBar"></div>
        {/if}
        <p style="margin-left: {wide ? "0" : "auto"};">{data.likes}</p>
        <img src="/icons/hearth.svg" alt="" />
    {/if}
</div>

<style lang="scss">
    .result {
        border: solid 1px var(--lightBorder);
        box-sizing: border-box;
        width: 100%;
        max-width: 1200px;
        padding: 0 30px;
        border-radius: 4px;
        height: 90px;

        a {
            font-family: "Fira sans";
            color: #fff;

            &:hover {
                color: var(--mainColor);
            }
        }

        small {
            color: gray;
            font-size: 12px;
            font-family: sans-serif;
            margin-top: 4px;
            margin-left: 12px;
        }

        .languageBar {
            width: 16px;
            height: 16px;
            border-radius: 50%;
            border: solid 1px gray;
            margin-left: 10px;
            margin-right: auto;
        }

        img {
            width: 20px;
            margin-left: 10px;
        }

        p {
            font-size: 15px;
            color: rgb(213, 215, 216);
        }
    }
</style>
