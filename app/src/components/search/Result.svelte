<script lang="ts">
    import Avatar from "$components/Avatar.svelte";
    import getColorOfLanguage from "$lib/colorOfLanguage";
    import type { ProjectDtoSimple } from "$lib/models/project/ProjectDtoSimple";

    export let data: ProjectDtoSimple;
    export let type: "user" | "project" | "mixed";
    export let wide: boolean = true;
</script>

<div class="result row" style="height: {wide ? '90' : '70'}px;">
    {#if type === "user"}
        <a href="/{data.username}">
            <Avatar
                username={data.username}
                size="{wide ? '42' : '35'}px"
                margin="0 20px 0 0"
                cursor="pointer"
            />
        </a>
        <div class="column" style="align-items: flex-start;margin-right: auto;">
            <a href="/{data.username}">{data.username}</a>
            <small style="margin-left: 0;min-width: max-content;"
                >{data.firstname} {data.lastname}</small
            >
        </div>
        <div class="row projects">
            <img src="/icons/project.svg" alt="" style="margin-right: 8px" />
            <p style="margin-right: auto;">{data.projectCount} Projects</p>
        </div>
        <div class="row followers">
            <img
                src="/icons/hearthOutline.svg"
                alt=""
                style="margin-right: 8px;"
            />
            <p>{data.followers} Followers</p>
        </div>
    {:else if type === "project"}
        <div class="row" style="margin-right: auto">
            <a href="/{data.owner.username}">
                <Avatar
                    username={data.owner.username}
                    size="{wide ? '42' : '35'}px"
                    margin="0 20px 0 0"
                    cursor="pointer"
                />
            </a>
            <div class="column" style="align-items: flex-start;">
                <a href="/project/{data.id}">{data.name}</a>
                <small>{data.created.slice(0, 10)}</small>
            </div>
        </div>
        {#if wide && data.mainLanguage && data.mainLanguage}
            <div class="row language">
                <div
                    class="languageBar"
                    style="background-color: {getColorOfLanguage(
                        data.mainLanguage
                    )};"
                ></div>
                <small>{data.mainLanguage}</small>
            </div>
        {/if}
        <p style="margin-left: {wide ? '0' : 'auto'};">{data.likes}</p>
        <img src="/icons/hearthOutline.svg" alt="" />
    {/if}
</div>

<style lang="scss">
    .result {
        border: solid 1px var(--lightBorder);
        box-sizing: border-box;
        width: 100%;
        max-width: 1200px;
        padding: 0 20px;
        border-radius: 4px;

        a {
            font-family: "Fira sans", sans-serif;
            color: #fff;

            &:hover {
                color: var(--mainColor);
            }
        }

        small {
            color: gray;
            font-size: 12px;
            font-family: sans-serif;
            margin-top: 3px;
        }

        .language {
            margin-right: auto;
            width: 100px;

            .languageBar {
                width: 16px;
                height: 16px;
                border-radius: 50%;
                margin-right: 12px;
            }

            small {
                margin-top: -1px;
            }
        }

        img {
            width: 20px;
            margin-left: 10px;
        }

        p {
            font-size: 15px;
            color: rgb(213 215 216);
            font-family: sans-serif;
        }
    }

    @media screen and (width <= 520px) {
        .projects {
            display: none;
        }

        .language {
            display: none;
        }
    }
</style>
