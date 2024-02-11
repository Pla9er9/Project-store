<script>
    import { onMount } from "svelte";

    let host = "";
    onMount(() => {
        host = "http://" + window.location.host;
    });
</script>

<footer class="row">
    <img src="/icons/project.svg" alt="" />
    <p>Project store 2024</p>
    <div class="row links">
        <a href="{host}/status">Server status</a>
        <a href="{host}/explore">Explore</a>
        <a href="{host}/login">Sign in</a>
        <a href="{host}/register">Sign up</a>
        <a href="{host}/report">Report error</a>
        <a href="{host}/console">Console</a>
    </div>
</footer>

<style lang="scss">
    footer {
        width: 100%;
        box-sizing: border-box;
        padding: 20px 45px;
        min-height: 80px;
        height: max-content;
        margin-top: 80px;
        border-top: 1px solid #202020;
        flex-wrap: wrap;
        justify-content: start;

        p {
            color: #dbdbdb;
            font-family: 'Fira sans';
            font-size: 15px;
            margin: 0 10px;
        }

        img {
            width: 18px;
            margin-right: 0px;
        }

        a {
            font-family: sans-serif;
            color: #9c9c9c;
            font-family: sans-serif;
            font-weight: 100;
            font-size: 13px;
            transition: color 0.1s;
            margin: 5px 16px;

            &:hover {
                color: rgb(195, 240, 128);
            }
        }
    }

    @media screen and (max-width: 764px) {
        .links {
            width: 100%;
            margin-top: 20px;
            flex-direction: column;
            align-items: start;

            a {
                max-width: 140px;
                margin-left: 5px;
                margin: 8px 0;
                width: 40%;
                text-align: start;
            }
        }
    }
</style>
