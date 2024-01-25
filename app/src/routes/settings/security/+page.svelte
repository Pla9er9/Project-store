<script>
    export let data

	import DevicesList from "$components/settings/security/DevicesList.svelte";
</script>

<main>
    <h1>Security</h1>
    <div style="display: flex;align-items:center; margin: 40px 0 8px 30px;">
        <img src="/icons/key.svg" width="24px" alt="key">
        <p>Logged in devices</p>
    </div>
    <DevicesList deviceList={data.data} />
</main>

<style lang="scss">
    main {
        h1 {
            font-family: 'Inter', sans-serif;
            font-size: 35px;
            margin-bottom: 50px;
            color: #fff;
        }

        p {
            margin-left: 10px;
            font-size: 18px;
            color: rgb(109, 109, 109);
            font-family: sans-serif;
            font-weight: 600;
        }
    }
</style>
