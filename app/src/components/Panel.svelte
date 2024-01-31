<script lang="ts">
    export let title: string;
    export let description: string;
    export let imageUrl: string;
    export let color: "green" | "cyan" | "red" | "white" = "white";
    export let style: string = "";
    export let animationActive = true;

    let animationTime = animationActive ? "12s" : "0";

    let finalColor: string = color;
    switch (color) {
        case "green":
            finalColor = "var(--mainColor)";
            break;
        case "red":
            finalColor = "var(--danger)";
            break;
    }
</script>

<div
    class="animation"
    style="--color: {finalColor};--aTime: {animationTime};{style}"
>
    <div class="panel row">
        <div class="column">
            <h1>{title}</h1>
            <p>{description}</p>
        </div>
        <img src={imageUrl} alt="" />
    </div>
</div>

<style lang="scss">
    .animation {
        position: relative;
        width: 95%;
        max-width: 1000px;
        height: 300px;
        margin: 100px 0;
        padding: 60px;
        overflow: hidden;
        border: solid 1px #181818;
        border-radius: 10px;

        &::before {
            content: "";
            position: absolute;
            left: 0;
            right: 0;
            margin: 0 auto;
            bottom: -550px;
            width: 80px;
            height: 1400px;
            background-color: var(--color);
            transform: rotate(105deg);
            animation: animate var(--aTime) linear infinite;
        }

        &::after {
            content: "";
            position: absolute;
            inset: 1px;
            background: var(--background);
            background: linear-gradient(
                90deg,
                var(--background) 0%,
                var(--color) 1500%
            );
            border-radius: 10px;
        }

        @keyframes animate {
            0% {
                transform: rotate(0deg);
            }

            100% {
                transform: rotate(360deg);
            }
        }

        .panel {
            width: 100%;
            height: 100%;

            * {
                z-index: 2;
            }

            .column {
                max-width: 200px;
                min-width: 60%;
                height: 300px;
                justify-content: start;

                h1 {
                    font-family: "Inter";
                    font-weight: 100;
                    margin-right: auto;
                    font-size: 45px;
                    color: var(--color);
                }

                p {
                    width: 80%;
                    max-width: 400px;
                    margin: 0 auto 0 10px;
                    color: rgb(170, 170, 170);
                    font-family: sans-serif;
                }
            }

            img {
                width: 150px;
                margin: auto 0;
                margin-left: auto;
            }
        }
    }
</style>
