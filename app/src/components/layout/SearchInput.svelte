<script lang="ts">
    import { onMount } from "svelte";

    let value = "";
    let focus = false;

    let isBottom = false
    let show = true

    function click() {
        isBottom = !isBottom
        const input = document.getElementById("searchInput")
        if (input) {
            if (isBottom) {
                input.style.display = "flex"
            } else {
                input.style.display = "none"                
            }
        }
    }

    onMount(() => {
        setInterval(() => {
            if (window.screenX >= 600) {
                isBottom = false
            }
        }, 50)
    })

</script>

{#if show}
    <div id="searchInput" style="top: {isBottom ? '80px' : '15px'};">
        <div class="icon">
            <img src="/icons/search_.svg" alt="" />
        </div>
        <input
            type="text"
            bind:value
            on:focusin={() => (focus = true)}
            on:focusout={async () => {
                await new Promise((r) => setTimeout(r, 500));
                focus = false;
            }}
            placeholder="Search"
        />
        {#if focus && value != ""}
            <div id="searchItems" class="column">
                <a class="searchItem" href="/search?q={value}&t=users" rel="external">
                    <img src="/icons/user.svg" alt="user icon" />
                    <p>Users {value}</p>
                </a>
                <a class="searchItem" href="/search?q={value}&t=project-name" rel="external">
                    <img src="/icons/project.svg" alt="tag icon" />
                    <p>Projects {value}</p>
                </a>
                <a class="searchItem" href="/search?q={value}&t=project-tag" rel="external">
                    <img src="/icons/tag.svg" alt="tag icon" />
                    <p>Projects with tag {value}</p>
                </a>
            </div>
        {/if}
    </div>
{/if}
<button id="searchIcon" on:click={click}>
    <img src="/icons/{isBottom ? 'cross_white' : 'search_'}.svg" alt="">
</button>

<style lang="scss">
    #searchInput {
        width: max-content;
        display: flex;
        align-items: center;
        position: absolute;
        left: 0;
        right: 0;
        margin: 0 auto;
        z-index: 9999;

        input {
            width: 25vw;
            max-width: 300px;
            height: 32px;
            justify-self: center;
            text-align: start;
            color: #dddcdc;
            text-indent: 10px;
            border: solid 1px var(--lightBorder);
            border-left: none;
            border-top-right-radius: 9px;
            border-bottom-right-radius: 9px;
            background-color: #141313;

            &::placeholder {
                text-align: center;
                text-indent: 0;
            }

            &:focus {
                outline: none;
                border: solid 1px rgba(182, 182, 182, 0.541);
                border-bottom-right-radius: 0px;
            }
        }

        #searchItems {
            position: absolute;
            top: 33px;
            left: 0;
            right: 0;
            margin: auto;
            align-items: end;

            .searchItem {
                width: 25vw;
                max-width: 304px;
                margin-left: auto;
                margin-right: 1px;
                height: 40px;
                border: none;
                background-color: #1413138e;
                backdrop-filter: blur(5px);
                outline: solid 1px #ffffff1a;
                cursor: pointer;
                display: flex;
                align-items: center;
                overflow: hidden;

                &:hover {
                    background-color: #2ddfff0c;
                }

                &:last-of-type {
                    border-bottom-left-radius: 5px;
                    border-bottom-right-radius: 5px;
                }

                img {
                    width: 18px;
                    margin-left: 10px;
                    margin-right: 20px;
                }

                p {
                    width: 80%;
                    text-align: start;
                    color: rgb(255, 243, 243);
                    font-size: 12px;
                    font-family: "Fira sans";
                    text-overflow: clip;
                }
            }
        }

        .icon {
            display: flex;
            align-items: center;
            justify-content: center;
            width: 30px;
            height: 34px;
            background-color: #141313;
            border: solid 1px var(--lightBorder);
            border-right: none;
            border-top-left-radius: 9px;
            border-bottom-left-radius: 9px;

            img {
                width: 60%;
                height: 60%;
            }
        }
    }

    button {
        display: none;
        background-color: var(--background);
        border: none;

        img {
            width: 18px;
            margin-top: 3px;
            margin-right: 10px;
        }
    }

    @media screen and (max-width: 980px) {
        #searchInput {
            display: none;

            input {
                width: 90vw;
            }

            #searchItems .searchItem {
                width: 90vw;
            }
        }
        #searchIcon {
            display: block !important;
        }
    }
</style>
