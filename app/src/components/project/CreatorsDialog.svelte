<script lang="ts">
	import Avatar from "$components/Avatar.svelte";
	import { PUBLIC_API_URL } from "$env/static/public";
import type { UserDtoSimple } from "$lib/models/user/UserDtoSimple";
	import { createEventDispatcher } from "svelte";

    export let creators: UserDtoSimple[]

    const dispatch = createEventDispatcher()

</script>

<div class="creators row">
    <button class="hideBtn" on:click={() => dispatch('hide')}>
        <div class="row">
            <img src="/icons/eye.svg" alt="" width="15px">
            Hide
        </div>
    </button>
    {#each creators as creator}
        <div class="row">
            <Avatar size="30px" imageUrl="{PUBLIC_API_URL}/user/{creator.username}/avatar" />
            <a href="/{creator.username}">{creator.username}</a>
        </div>
    {/each}
</div>

<style lang="scss">
    .creators {
        width: 100%;
        max-width: 710px;
        height: max-content;
        flex-wrap: wrap;
        padding: 12px 20px;
        margin-bottom: 20px;
        background-color: #111111;
        border: solid 1px #ffffff11;
		border-radius: 9px;
        top: 150px;
        z-index: 999;

        .hideBtn {
            background-color: inherit;
            border: solid 1px var(--mainColor);
            color: #fff;
            cursor: pointer;
            padding: 0 14px;
            margin-right: 25px;
            border-radius: 4px;
            font-family: monospace;
            font-size: 12px;

            .row {
                margin: 0;

                img {
                    margin-right: 6px;
                }
            }

        }

        .row {
            height: 30px;
            margin: 12px;

            a {
                margin: 0 15px 0 10px;
                font-family: sans-serif;
                color: #fff;
    
                &:hover {
                    color: var(--mainColor);
                }
            }
        }
    }
</style>