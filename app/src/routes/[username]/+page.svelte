<script lang="ts">
    export let data;

    import AvatarName from "$components/AvatarName.svelte";
    import SwitchButton from "$components/project/SwitchButton.svelte";
    import Readme from "$components/project/files/Readme.svelte";
    import UserProjects from "$components/user/UserProjects.svelte";

    let switchValue = "Description";
</script>

<svelte:head>
    <title>{data.data.username}</title>
</svelte:head>

<main>
    <AvatarName userdata={data.data} avatarSize="140px" />
    <div class="links">
        {#each data.data.personalLinks as link}
            {#if link !== ""}
                <div class="link">
                    <img src="/icons/link.svg" alt="link" />
                    <a href={link}>{link}</a>
                </div>
            {/if}
        {/each}
    </div>
    <SwitchButton
        bind:currentValue={switchValue}
        values={["Description", "Projects"]}
    />
    {#if switchValue === "Projects"}
        <UserProjects userId={data.data.userId} />
    {:else if switchValue === "Description" && data.data.description && data.data.description != ""}
        <Readme readme={data.data.description} />
    {/if}
</main>

<style lang="scss">
    main {
        width: 90%;
        max-width: 1000px;
        margin: 0 auto;
        margin-top: 30px;

        .links {
            margin-top: 20px;
            margin-left: 20px;
            display: flex;
            flex-direction: column;

            .link {
                display: flex;
                align-items: center;
                margin: 2px 0;

                a {
                    max-width: 220px;
                    overflow-x: auto;
                    font-size: 15px;
                    font-family: sans-serif;
                    font-weight: 300;
                    color: rgb(0 110 255);
                    text-decoration: none;
                }

                img {
                    width: 20px;
                    margin-top: 2px;
                    margin-right: 8px;
                }
            }
        }
    }
</style>
