<script lang="ts">
    export let text: string;
    export let link: string;
</script>

<a class="linkBtn" href={link}>
    <p>{text}</p>
</a>

<style lang="scss">
    .linkBtn {
        width: 120px;
        height: 31px;
        display: flex;
        align-items: center;
        justify-content: center;
        transition: 150ms;

        p {
            color: #d8d8d8;
            font-family: "Fira Sans", sans-serif;
            font-size: 13px;
        }

        &:hover {
			outline: solid 1px var(--mainColor);
			border-radius: 9px;
        }
    }
</style>
