<script lang="ts">
    import getColorOfLanguage from "$lib/colorOfLanguage";
    import type { Language } from "$lib/models/project/Language";

    export let languages: Language[];

    let sum = 0;
    languages.forEach((e) => {
        sum += e.filesCount;
    });

    let i = -1;
    let colors: string[] = [];
    for (let i = 0; i < languages.length; i++) {
        colors.push(
            getColorOfLanguage(languages[languages.length - 1 - i].name)
        );
    }

    function getColor() {
        i += 1;
        if (i === languages.length) i = 0;
        return colors[i];
    }
</script>

{#if languages.length > 0}
    <div id="languageBox" class="column">
        <div class="inline row">
            {#each languages.slice().reverse() as lang}
                <div
                    class="line"
                    style="width: {(lang.filesCount / sum) *
                        100}%;background: {getColor()}"
                />
            {/each}
        </div>
        <div class="inline row" style="margin-left: 8px;">
            {#each languages.slice().reverse() as lang}
                <div class="row" style="min-width: max-content">
                    <div class="circle" style="background: {getColor()};" />
                    <p>
                        {lang.name !== "" ? lang.name : "other"}
                        <span
                            >{String((lang.filesCount / sum) * 100).slice(
                                0,
                                4
                            )}%</span
                        >
                    </p>
                </div>
            {/each}
        </div>
    </div>
{/if}

<style lang="scss">
    #languageBox {
        max-width: 750px;
        width: 90%;
        height: 70px;
        background: #111;
        border: solid 1px #ffffff11;
        border-radius: 9px;
        justify-content: space-around;
        padding-bottom: 8px;

        .inline {
            width: 94%;
            overflow-x: auto;

            .line {
                margin-top: 16px;
                background-color: #fff;
                height: 6px;

                &:first-of-type {
                    border-top-left-radius: 8px;
                    border-bottom-left-radius: 8px;
                }

                &:last-of-type {
                    border-top-right-radius: 8px;
                    border-bottom-right-radius: 8px;
                }
            }

            .circle {
                width: 14px;
                height: 14px;
                border-radius: 50%;
                margin-right: 8px;
                background-color: #fff;
            }

            p {
                font-family: "Fira sans";
                font-size: 13px;

                span {
                    font-size: 12px;
                    margin: 0 16px 0 2px;
                    color: #d3d3d3;
                }
            }
        }
    }
</style>
