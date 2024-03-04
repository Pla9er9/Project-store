<script lang="ts">
    import CheckBox from "$components/forms/CheckBox.svelte";
    import IssueRow from "$components/project/issues/IssueRow.svelte";
    import NewIssueForm from "$components/project/issues/NewIssueForm.svelte";
    import BackBtn from "$components/BackBtn.svelte";
    import Observer from "$components/Observer.svelte";
    import fetchHttp from "$lib/fetchHttp.js";
    import { alertStore } from "$lib/stores/alertStore.js";
    import type { IssueDtoSimple } from "$lib/models/issue/IssueDtoSimple.js";

    export let data;

    let formActive = false;
    let showClosed = false;
    let apiPage = 0;
    let issues: IssueDtoSimple[] = data.data.content;

    function changeFormVisibility() {
        const body = document.querySelector("body");
        formActive = !formActive;
        if (body !== undefined && body !== null) {
            if (formActive) {
                body.style.overflowY = "hidden";
            } else {
                body.style.overflowY = "auto";
            }
        }
    }

    async function loadMore() {
        if (data.data.totalPages === 0 || apiPage === data.data.totalPages - 1)
            return;
        apiPage += 1;
        const res = await fetchHttp(
            `/project/${data.project_data.id}/issues?page=${apiPage}`,
            {}
        );
        if (!res.ok) {
            alertStore.update((a) => {
                a.message = "Could not load more issues";
                a.color = "red";
                return a;
            });
        }
        issues = [...issues, res.body.content];
    }

    function addIssue() {
        changeFormVisibility();
        window.location.reload();
    }
</script>

<main>
    {#if formActive}
        <button on:click={changeFormVisibility} class="dark" />
        <NewIssueForm on:submit={addIssue} projectId={data.slug} />
    {/if}
    <div class="issues">
        <div class="topbar">
            <BackBtn
                callback={() =>
                    location.replace(location.href.replace("issues", ""))}
            />
            <p>{data.project_data?.name} issues ({issues.length})</p>
            <CheckBox
                label="Show closed"
                marginLeft="15px"
                border="none"
                bind:value={showClosed}
            />
            <button class="newIssue" on:click={changeFormVisibility}
                >New issue</button
            >
        </div>
        {#each issues as issue}
            {#if issue.isOpen || showClosed}
                <IssueRow {issue} />
            {/if}
        {/each}
        {#if apiPage !== data.data.totalPages - 1}
            <Observer onvisible={loadMore} />
        {/if}
    </div>
</main>

<style lang="scss">
    main {
        margin-top: 50px;

        .dark {
            position: absolute;
            top: -3px;
            left: -3px;
            width: 100vw;
            height: 100%;
            border: none;
            background-color: #000000d0;
            z-index: 9999;
        }

        .issues {
            margin: 0 auto;
            width: 90%;
            max-width: 1000px;

            .topbar {
                display: flex;
                align-items: center;

                p {
                    font-size: 25px;
                }

                .newIssue {
                    width: 90px;
                    height: 30px;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    margin-left: auto;
                    color: #fff;
                    font-family: sans-serif;
                    border: none;
                    cursor: pointer;
                    outline: solid 1px #ffffff3d;
                    border-radius: 5px;
                    background: inherit;
                    transition: 100ms ease-in-out;

                    &:hover {
                        background-color: rgba(192, 192, 192, 0.137);
                        outline: solid 1px gray;
                    }
                }
            }
        }
    }
</style>
