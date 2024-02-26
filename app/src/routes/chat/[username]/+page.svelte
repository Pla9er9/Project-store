<script lang="ts">
    export let data;

    import { onMount } from "svelte";
    import { CompatClient, Stomp, type IMessage } from "@stomp/stompjs";
    import SockJS from "sockjs-client";
    import MessageInput from "$components/chat/MessageInput.svelte";
    import Message from "$components/chat/Message.svelte";
    import { beforeNavigate, goto } from "$app/navigation";
    import ChatList from "$components/home/ChatList.svelte";
    import Avatar from "$components/Avatar.svelte";

    let stompClient: CompatClient;
    let username: string;

    let message = "";
    let messages: any[] = [];

    if (data.token) {
        username = JSON.parse(atob(data.token.split(".")[1])).sub;
    } else {
        goto("login");
    }

    var authHeaders = {
        Authorization: `Bearer ${data.token}`,
    };

    onMount(() => {
        const body = document.querySelector("body");
        const sock = new SockJS("http://localhost:8080/ws", {
            transportOptions: {
                "xhr-streaming": {
                    headers: authHeaders,
                },
            },
        });
        stompClient = Stomp.over(sock);
        stompClient.connect({ headers: authHeaders }, onConnected, () =>
            alert("Wystąpił błąd")
        );

        if (body) {
            body.style.overflow = "hidden";
        }
        const messagesDiv = document.querySelector(".main");
        if (messagesDiv) {
            messagesDiv.scrollTo(0, messagesDiv.scrollHeight);
        }
    });

    beforeNavigate(() => {
        const body = document.querySelector("body");
        if (body) {
            body.style.overflow = "auto";
        }
    });

    function onConnected() {
        stompClient.subscribe(
            `/user/${username}/queue/messages`,
            onMessageReceived
        );
        stompClient.subscribe(`/user/public`, onMessageReceived);
    }
    
    async function onMessageReceived(payload: IMessage) {
        var message = JSON.parse(payload.body);
        messages = [...messages, message];
        await new Promise((r) => setTimeout(r, 50));
        const messagesDiv = document.querySelector(".main");
        if (messagesDiv) {
            messagesDiv.scrollTo(0, messagesDiv.scrollHeight);
        }
    }

    async function sendMessage() {
        var messageContent = message.trim();
        if (messageContent && stompClient) {
            var chatMessage = {
                senderUsername: username,
                recipientUsername: data.username,
                content: messageContent,
            };
            stompClient.send(`/app/chat`, {}, JSON.stringify(chatMessage));
            messages = [...messages, chatMessage];
            await new Promise((r) => setTimeout(r, 50));
            const messagesDiv = document.querySelector(".main");
            if (messagesDiv) {
                messagesDiv.scrollTo(0, messagesDiv.scrollHeight);
            }
        }
        message = "";
    }

    if (data.messages != undefined) {
        messages = data.messages;
    }
</script>

<svelte:head>
    <title>Chat - {data.username}</title>
</svelte:head>

<main class="row">
    <div id="chatList">
        <ChatList />
    </div>
    <div class="chat column">
        <div class="messages">
            <div id="uInfo" class="column">
                <Avatar
                    username={data.username}
                    margin="0"
                    size="85px"
                    cursor="pointer"
                />
                <a href="/{data.username}">
                    <p>{data.username}</p>
                </a>
            </div>
            {#each messages as message}
                <Message {message} {username} />
            {/each}
        </div>
        <MessageInput bind:value={message} sendMessage={() => sendMessage()} />
    </div>
</main>

<style lang="scss">
    main {
        height: 100vh;
        align-items: start;
        justify-content: space-between;
        padding: 30px;
        box-sizing: border-box;

        #chatList {
            width: 300px;
            height: calc(100vh - 120px);
            max-height: 800px;
        }

        .chat {
            width: calc(100% - 350px);
            max-width: 1500px;
            height: calc(100vh - 120px);
            max-height: 800px;
            margin: 0 auto;
            justify-content: center;

            #uInfo {
                width: 200px;
                margin: 20px auto;

                p {
                    font-size: 22px;
                    font-family: "Inter";
                    font-weight: 100;

                    &:hover {
                        text-decoration: underline var(--mainColor) 1px;
                    }
                }
            }

            .messages {
                margin-top: auto;
                width: 100%;
                height: 95%;
                overflow-y: auto;
            }
        }
    }

    @media screen and (max-width: 800px) {
        #chatList {
            display: none;
        }
        .chat {
            width: 100% !important;
        }
    }
</style>
