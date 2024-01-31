<script>
    import { goto } from "$app/navigation";

    export let data;

    import AnimatedPanel from "$components/starterPage/AnimatedPanel.svelte";
    import MainText from "$components/starterPage/MainText.svelte";
    import Panel from "$components/Panel.svelte";
    import { onMount } from "svelte";

    onMount(() => {
        if (data.token) {
            goto("/home");
        }
    });
</script>

<main>
    <MainText />
    <AnimatedPanel />
    <div style="margin-top: 200px;"></div>
    <Panel
        title="Amazing Projects"
        description="Create and manage projects. Edit code in browser, create issues, post releases. Open source world stars here!"
        imageUrl="/icons/project.svg"
    />
    <Panel
        title="Users Profiles"
        description="Create your owne profile with rich description and amazing projects. Follow and watch others accounts."
        imageUrl="/icons/user.svg"
		color="cyan"
        animationActive={false}
    />
    <Panel
        title="Realtime Chat"
        description="Chat with others. Send messages quickly and safely."
        imageUrl="/icons/globe.svg"
		color="green"
    />
</main>

<style lang="scss">
    main {
        display: flex;
        flex-direction: column;
        align-items: center;
        max-width: 1400px;
        margin: 0 auto;
    }
</style>
