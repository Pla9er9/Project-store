<script lang="ts">
    export let values: string[];
    export let currentValue: string;
</script>

<div class="{values.length > 2 ? 'large' : ''}">
    {#each values as value}
        <button
            style="background: {currentValue === value
                ? '#8eed4521'
                : 'inherit'}"
            on:click={() => (currentValue = value)}>{value}</button
        >
    {/each}
</div>

<style lang="scss">
    button {
        width: 120px;
        height: 40px;
        margin: 50px 0;
        border: none;
        color: #fff;
        font-family: "Fira sans", sans-serif;
        border-top: solid 1px var(--lightBorder);
        border-bottom: solid 1px var(--lightBorder);
        transition: background 150ms ease-in-out;
        cursor: pointer;
        --margin: 6px;

        &:first-child {
            border-top-left-radius: var(--margin);
            border-bottom-left-radius: var(--margin);
            border-left: solid 1px var(--lightBorder);
        }

        &:last-child {
            border-top-right-radius: var(--margin);
            border-bottom-right-radius: var(--margin);
            border-right: solid 1px var(--lightBorder);
        }
    }

    @media screen and (width <= 800px) {
        .large {
            width: 90%;
            margin: 40px auto 50px;

            button {
                width: 100%;
                height: 45px;
                border: solid 1px var(--lightBorder);
                margin: 0;
                border-radius: 0 !important;
                text-align: start;
                text-indent: 20px;

                &:first-child {
                    border-top-left-radius: 5px !important;
                    border-top-right-radius: 5px !important;
                    border-left: solid 1px var(--lightBorder);
                }

                &:last-child {
                    border-bottom-left-radius: 5px !important;
                    border-bottom-right-radius: 5px !important;
                    border-right: solid 1px var(--lightBorder);
                }
            }
        }
    }
</style>
