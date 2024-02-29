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
    </div>
    {#if token !== undefined}
        {#if userdata.username != JSON.parse(atob(token.split(".")[1])).sub}
            <div id="actionBtns" class="row" style="margin-left: auto;">
                <FollowBtn
                    width={followBtnWidth}
                    following={userdata.followed}
                    onFollow={follow}
                    onUnfollow={unfollow}
                />
                <a
                    href={location.href.replace(
                        location.host,
                        location.host + "/chat"
                    )}
                    class="msgBtn row"
                >
                    Chat
                </a>
            </div>
        {/if}
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
