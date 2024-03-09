<script lang="ts">
    import { goto } from "$app/navigation";

    export let userdata: UserDto;
    export let avatarSize = "100px";

    import fetchHttp from "$lib/fetchHttp";
    import { get } from "svelte/store";
    import Avatar from "./Avatar.svelte";
    import FollowBtn from "./user/FollowBtn.svelte";
    import { tokenStore } from "$lib/stores/tokenStore";
    import { onMount } from "svelte";
    import type { UserDto } from "$lib/models/user/UserDto";
    import { alertStore } from "$lib/stores/alertStore";

    const token = get(tokenStore);

    var name = "";
    if (userdata.firstname !== null && userdata.lastname !== null) {
        name = userdata.firstname + " " + userdata.lastname;
    }

    async function follow() {
        if (token === undefined) {
            await goto("login");
            return;
        }
        await fetchHttp(`/user/${userdata.username}/follow`, {
            method: "post",
            token: token,
        });

        userdata.followed = true;
        userdata.followers += 1;
    }

    async function unfollow() {
        if (token === undefined) {
            await goto("login");
            return;
        }
        await fetchHttp(`/user/${userdata.username}/unfollow`, {
            method: "put",
            token: token,
        });

        userdata.followed = false;
        userdata.followers -= 1;
    }

    async function unblock() {
        const res = await fetchHttp(`/user/${userdata.username}/unblock`, {
            token: get(tokenStore),
            method: "put"
        })
        if (!res.ok) {
            alertStore.update(a => {
                a.message = "Could not unblock user, try later"
                a.color = "red"
                return a
            })
            return
        }
        userdata.blocked = false
    }

    async function block() {
        const res = await fetchHttp(`/user/${userdata.username}/block`, {
            token: get(tokenStore),
            method: "post"
        })
        if (!res.ok) {
            alertStore.update(a => {
                a.message = "Could not block user, try later"
                a.color = "red"
                return a
            })
            return
        }
        userdata.blocked = true
    }

    let followBtnWidth = "120px";

    onMount(() => {
        if (window.innerWidth <= 476) {
            followBtnWidth = "250px";
            avatarSize = "80px";
        }

        onresize = () => {
            if (window.innerWidth <= 476) {
                followBtnWidth = "250px";
                avatarSize = "80px";
            } else {
                followBtnWidth = "120px";
                avatarSize = "100px";
            }
        };
    });
</script>

<div class="avatarName">
    <Avatar size={avatarSize} username={userdata.username} />
    <div class="textData column">
        <h1>{userdata.username}</h1>
        <p>{name}</p>
        <div class="follows">
            <p>{userdata.followers} followers {userdata.following} following</p>
        </div>
        {#if token && userdata.username != JSON.parse(atob(token.split(".")[1])).sub}
            <button on:click={userdata.blocked ? unblock : block}>{userdata.blocked ? "Unblock" : "Block"} {userdata.username}</button>
        {/if}
    </div>
    {#if token && userdata.username != JSON.parse(atob(token.split(".")[1])).sub}
        <div id="actionBtns" class="row" style="margin-left: auto;">
            <FollowBtn
                width={followBtnWidth}
                following={userdata.followed}
                onFollow={follow}
                onUnfollow={unfollow}
            />
            <a href="/chat/{userdata.username}" class="msgBtn row">Chat</a>
        </div>
    {/if}
</div>

<style lang="scss">
    .avatarName {
        width: 100%;
        max-width: 900px;
        display: flex;
        flex-wrap: wrap;
        align-items: center;

        .textData {
            margin-left: 30px;
            margin-right: 40px;
            align-items: start;

            h1 {
                margin: 0;
                color: #fff;
                font-family: "Fira sans";
            }

            p {
                margin: 0;
                font-family: "Fira sans";
                font-size: 14px;
                color: rgb(185, 185, 185);
            }

            .follows {
                display: flex;
                align-items: center;
                justify-content: space-around;

                p {
                    margin-top: 4px;
                    color: var(--mainColor);
                    font-size: 10px;
                }
            }

            button {
                margin: 3px 0 -1px 0;
                background: transparent;
                padding: 0;
                border: none;
                cursor: pointer;
                font-size: 11px;
                color: var(--danger);
                font-family: 'Fira sans';

                &:hover {
                    color: red;
                }
            }
        }

        .msgBtn {
            width: 72px;
            height: 30px;
            margin-left: 8px;
            background-color: #f1f1f1;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            justify-content: center;
            font-weight: 600;
            font-family: sans-serif;
            color: #000;
        }
    }

    @media screen and (max-width: 512px) {
        .avatarName {
            justify-content: center;
            margin-top: -15px;
        }

        #actionBtns {
            width: 90%;
            max-width: 360px;
            display: flex;
            margin: 10px auto;
            justify-content: center;
        }

        .msgBtn {
            min-width: 50%;
        }
    }
</style>
