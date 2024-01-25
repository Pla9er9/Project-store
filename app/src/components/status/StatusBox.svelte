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
        border: solid 1px #ffffff48;
        border-radius: 5px;

        h1 {
            margin: auto 0;
            font-size: 19px;
            color: #fff;
            font-family: sans-serif;
        }

        img {
            width: 20px;
            margin: 0 15px;
        }

        .statusBar {
            width: 70px;
            height: 100%;
            margin-left: auto;
            border-top-right-radius: 5px;
            border-bottom-right-radius: 5px;
        }

        .working {
            background-image: linear-gradient(90deg, rgb(40, 161, 46), var(--mainColor));
        }

        .notworking {
            background-image: linear-gradient(90deg, rgb(223, 46, 105), rgb(255, 38, 0));
        }
    }
</style>
