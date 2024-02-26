<script lang="ts">
    export let value: string;
    export let sendMessage: () => void;

    function keyPressed(key: KeyboardEvent) {
        if (key.key === "Enter") {
            sendMessage();
            value = "";
        }
    }

    let files: FileList
</script>


<div class="column">
    <div class="row" style="outline: none;">
        {#if files}
            {#each Array.from(files) as img}
                <p>{img.name}</p>
            {/each}
        {/if}
    </div>
    <div class="row">
        <input
            type="file"
            class="custom-file-input"
            accept="image/png, image/gif, image/jpeg"
            bind:files
        />
        <input bind:value on:keypress={keyPressed} />
        <button on:click={sendMessage} type="submit">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"
                ><path d="M2.01 21L23 12L2.01 3L2 10l15 2l-15 2z" /></svg
            >
        </button>
    </div>
</div>

<style lang="scss">
    .column {
        max-width: 1400px;
        width: 95%;
        height: 40px;
        display: flex;
        outline: solid 1px var(--lightBorder);
        border-radius: 10px;
        background-color: var(--background);
    }
    
    .row {
        width: 100%;
        * {
            border-radius: 10px;
        }

        &:has(input:focus) {
            outline: solid 1px #acacac;
            border-radius: 10px;
        }

        input:not(.custom-file-input) {
            width: 100%;
            height: 100%;
            border: none;
            text-indent: 15px;
            margin: 0;
            background-color: var(--background);
            outline: none;
            color: #fff;
            border-bottom-right-radius: 0;
        }

        .custom-file-input {
            overflow-x: hidden;
            width: 40px;
            margin-left: 8px;
        }

        .custom-file-input::-webkit-file-upload-button {
            visibility: hidden;

        }
        .custom-file-input::before {
            width: 30px;
            height: 26px;
            margin: 5px 0 0 2px;
            content: "";
            display: inline-block;
            background: linear-gradient(top, #f9f9f9, #e3e3e3);
            border: 1px solid var(--lightBorder);
            border-radius: 5px;
            background-image: url('/icons/image_add.svg');
            background-size: 16px;
            background-repeat: no-repeat;
            background-position: center;
            white-space: nowrap;
            -webkit-user-select: none;
            user-select: none;
            cursor: pointer;
        }

        button {
            width: 50px;
            height: calc(100% + 2px);
            display: flex;
            align-items: center;
            justify-content: center;
            font-family: "Fira sans";
            transition: fill 250ms ease-in-out;
            background-color: var(--background);
            cursor: pointer;
            border: none;
            border-bottom-left-radius: 0;

            &:hover svg {
                fill: var(--mainColor);
            }

            svg {
                width: 20px;
                height: 20px;
                fill: #fff;
            }
        }
    }
</style>
