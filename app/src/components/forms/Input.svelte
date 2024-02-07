<script lang="ts">
    export let placeholder: string;
    export let password = false;
    export let value = "";
    export let validator: (s: string) => string = (str) => "";
    export let maxlength = 30;
    export let correct = false;
    export let width = "220px";

    let clicked = false;
    let type = "text";
    if (password) {
        type = "password";
    }

    function typeAction(node: HTMLInputElement) {
        node.type = type;
    }
</script>

<div class="column" style="width: max-content;">
    <div
        class="input row"
        style="
        --state: {correct ? 'var(--mainColor)' : 'var(--danger)'};
        --border: {correct || !clicked ? 'var(--lightBorder)' : 'var(--danger)'};
        width: {width};
        "
    >
        <slot />
        <input
            spellcheck="false"
            bind:value
            on:keyup={() => (correct = validator(value) === "")}
            on:click={() => (clicked = true)}
            use:typeAction
            {maxlength}
            {placeholder}
        />
    </div>
    {#if !correct && clicked}
        <small class="errorMsg">{validator(value)}</small>
    {/if}
</div>

<style lang="scss">
    .input {
        width: 100%;
        margin: 10px 0;
        padding-left: 24px;
        height: 40px;
        border: solid 1px var(--border);
        border-radius: 8px;
        background-color: inherit;
        pointer-events: none;
        transition: 250ms;

        &:focus-within {
            border: solid 1px var(--state);
        }

        input {
            height: 38px;
            background-color: inherit;
            color: #fff;
            font-family: "Fira Sans", sans-serif;
            text-indent: 35px;
            outline: none;
            border: none;
            border-radius: 9px;
            pointer-events: auto;
        }
    }

    .errorMsg {
        color: var(--dark-danger);
        font-size: 10px;
        font-family: sans-serif;
        margin: 0 auto 5px 10px;
    }
</style>
