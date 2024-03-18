<script lang="ts">
    export let value: string;
    export let sendMessage: () => void;
    export let files: File[] = []

    function keyPressed(key: KeyboardEvent) {
        if (key.key === "Enter") {
            sendMessage();
            value = "";
        }
    }

    function deleteFromFileList(filename: string) {
        files = files.filter((f) => f.name !== filename)
    }
 
    async function convert() {
        await new Promise(r => setTimeout(r, 100));
        if (_files) {
            files = Array.from(_files)
        }
    }

    let _files: FileList;
</script>

<div class="column">
    <div class="row" style="outline: none;min-height: 80px;">
        {#if files}
            {#each Array.from(files) as img}
                <button
                    on:click={() => deleteFromFileList(img.name)}
                    class="imgSelected"
                    style="background-image: url('{URL.createObjectURL(img)}');"
                >
                </button>
            {/each}
        {/if}
    </div>
    <div class="row">
        <input
            type="file"
            class="custom-file-input"
            accept="image/png, image/gif, image/jpeg"
            multiple
            on:input={convert}
            bind:files={_files}
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
        background-color: transparent;
    }

    .row:last-of-type {
        outline: solid 1px var(--lightBorder);
        border-radius: 10px;
    }

    .row {
        width: 100%;
        background-color: transparent;

        * {
            border-radius: 10px;
        }

        &:has(input:focus) {
            outline: solid 1px #acacac;
        }

        input:not(.custom-file-input) {
            width: 100%;
            height: 100%;
            border: none;
            text-indent: 8px;
            margin: 0 0 0 10px;
            background-color: var(--background);
            outline: none;
            color: #fff;
            border-bottom-right-radius: 0;
        }

        .custom-file-input {
            overflow-x: hidden;
            width: 40px;
            margin-left: 8px;
            z-index: 99;
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
            background: linear-gradient(to top, #f9f9f9, #e3e3e3);
            border: 1px solid var(--lightBorder);
            border-radius: 5px;
            background-image: url("/icons/image_add.svg");
            background-size: 16px;
            background-repeat: no-repeat;
            background-position: center;
            cursor: pointer;
        }

        .imgSelected {
            width: 50px;
            height: 50px;
            border: solid 1px var(--lightBorder);
            margin: 15px 6px;
            border-radius: 10px;
            background-position: center;
            background-repeat: no-repeat;
            background-size: cover;

            &:hover {
                border: solid 1px var(--dark-danger);
            }
        }

        button {
            width: 50px;
            height: calc(100% + 2px);
            display: flex;
            align-items: center;
            justify-content: center;
            font-family: "Fira sans", sans-serif;
            transition: fill 250ms ease-in-out;
            background-color: var(--background);
            cursor: pointer;
            border: none;
            border-bottom-left-radius: 0;

            svg {
                width: 20px;
                height: 20px;
                fill: #fff;
            }

            &:hover svg {
                fill: var(--mainColor);
            }
        }
    }
</style>
