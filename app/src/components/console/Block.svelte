<script lang="ts">
    export let gradientColor: string;
    export let link: string;
    export let icon: string;
    export let header: string;
    export let smallText: string;
</script>

<a href={link} class="row" style="--grad: {gradientColor}">
    <h1>{header}<br /><span>{smallText}</span></h1>
    <img src={icon} alt="" />
</a>

<style lang="scss">
    a {
        width: 99vw;
        max-width: 340px;
        min-height: 140px;
        height: max-content;
        border: solid 1px var(--lightBorder);
        border-radius: 5px;
        margin: 10px;
        box-sizing: border-box;
        justify-content: space-around;
        background: linear-gradient(
            130deg,
            var(--background) 0%,
            var(--grad) 1400%
        );
        transition: border 100ms ease-in-out;

        &:hover {
            border: solid 1px #8f8f8f;
        }

        h1 {
            font-family: Inter, serif;
            font-weight: 100;
            line-height: 30px;
            font-size: 28px;

            span {
                font-size: 20px;
                color: rgb(187 186 186);
            }
        }

        img {
            width: 45px;
        }
    }
</style>
