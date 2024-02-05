<script>
	import { onMount } from "svelte";

    let host = ""
    onMount(() => {
        host = "http://" + window.location.host
    })
</script>

<footer class="row">
    <img src="/icons/project.svg" alt="">
    <p>Project store 2024</p>
    <a href="{host}/status">Server status</a>
    <a href="{host}/explore">Explore</a>
    <a href="{host}/login">Sign in</a>
    <a href="{host}/register">Sign up</a>
    <a href="{host}/report">Report error</a>
</footer>

<style lang="scss">
    footer {
        width: 100%;
        padding: 10px 45px;
        margin-top: auto;
        border-top: 1px solid #202020;
        height: 60px;
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
            transition: .1s;
            margin: 5px 16px;

            &:hover {
                color: rgb(195, 240, 128);
            }
        }
    }    
</style>
