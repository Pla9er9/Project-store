<script>
    export let data;

    import DevicesList from "$components/settings/security/DevicesList.svelte";

    let expand = false;
</script>

<main>
    <h1 class="settingsHeader">Security</h1>
    <div class="column section">
        <div class="row top" style="margin: 0 0 8px 30px;">
            <div class="row">
                <img src="/icons/key_green.svg" width="24px" alt="key" />
                <p>Logged in devices</p>
            </div>
            <button on:click={() => expand = !expand}>{expand ? "hide" : "show"}</button>
        </div>
        <DevicesList deviceList={data.data} {expand} />
    </div>
</main>

<style lang="scss">
    main {
        p {
            margin-left: 14px;
            font-size: 18px;
            color: rgb(188, 185, 185);
            font-family: 'Fira sans';
        }

        .section {
            max-width: 620px;
            width: 100vw;
        }

        .top {
            width: 100%;
            justify-content: space-between;

            button {
                width: 80px;
                height: 30px;
                border: solid 1px var(--lightBorder);
                background: none;
                border-radius: 5px;                
                color: var(--mainColor);
                transition: 150ms;
                cursor: pointer;

                &:hover {
                    background-color: rgba(128, 255, 0, 0.051);
                    color: #fff;
                }
            }
        }

    }
</style>
