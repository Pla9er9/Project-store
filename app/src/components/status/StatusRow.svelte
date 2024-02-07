<script lang="ts">
    export let serviceName: string
    export let working: boolean
    export let iconName: string
</script>

<div class="statusBox">
    <img src="icons/{iconName}" alt="">
    <h1>{serviceName}</h1>
    <div class="statusBar {working ? "working" : "notworking"}"></div>
</div>

<style lang="scss">
    .statusBox {
        width: 90%;
        height: 60px;
        margin: 12px;
        display: flex;
        align-items: center;
        border: solid 1px var(--lightBorder);
        border-radius: 5px;

        h1 {
            margin: auto 0;
            font-size: 14px;
            color: #fff;
            font-family: sans-serif;
        }

        img {
            width: 20px;
            margin: 0 15px;
        }

        .statusBar {
            width: 32px;
            height: 32px;
            margin: 0 20px 0 auto;
            border-radius: 10px;
        }

        .working {
            background-image: linear-gradient(90deg, rgb(64, 199, 71), var(--mainColor));
        }

        .notworking {
            background-image: linear-gradient(270deg, rgb(223, 46, 105), rgb(248, 91, 64));
        }
    }
</style>
