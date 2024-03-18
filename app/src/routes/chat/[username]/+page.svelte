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
    import { getNameByPath } from "$lib/utils/fileUtils.js";
    import { alertStore } from "$lib/stores/alertStore.js";
    import Observer from "$components/Observer.svelte";
    import fetchHttp from "$lib/fetchHttp.js";
    import { get } from "svelte/store";
    import { tokenStore } from "$lib/stores/tokenStore";
    import Button from "$components/Button.svelte";
    import type { MessageDto } from "$lib/models/message/MessageDto.js";

    let stompClient: CompatClient;
    let username: string;

    let message = "";
    let files: File[] = [];
    let messages: MessageDto[] = [];
    let apiPage = 0;
    let auto = false
    let active = true

    if (data.messages != undefined) {
        messages = data.messages.content
    }
    
    if (data.token) {
        username = JSON.parse(atob(data.token.split(".")[1])).sub;
    } else {
        goto("login");
    }

    var authHeaders = {
        Authorization: `Bearer ${data.token}`,
    };

    onMount(async () => {
        const body = document.querySelector("body");
        const sock = new SockJS("http://localhost:8080/ws", {
            transportOptions: {
                "xhr-streaming": {
                    headers: authHeaders,
                },
            },
        });
        stompClient = Stomp.over(sock);
        stompClient.connect(
            { headers: authHeaders },
            onConnected,
            () => alert("Error"),
            (e: Event) => {
                if (active) {
                    console.error(e);
                    alertStore.update((a) => {
                        a.color = "red";
                        a.message = "Connection with server closed";
                        return a;
                    });
                }
            }
        );

        if (body) {
            body.style.overflow = "hidden";
        }
        scrollDown();
    });

    beforeNavigate(() => {
        active = false
        const body = document.querySelector("body");
        if (body) {
            body.style.overflow = "auto";
        }
        stompClient.deactivate()
    });

    function onConnected() {
        stompClient.subscribe(
            `/user/${username}/queue/messages`,
            onMessageReceived
        );
        stompClient.subscribe(`/user/public`, onMessageReceived);
    }

    async function onMessageReceived(payload: IMessage) {
        if (payload.body === "\"blocked\"") {
            return alertStore.update(a => {
                a.message = `You have been blocked by ${data.username}`
                a.color = "yellow"
                return a
            })
        }
        var message = JSON.parse(payload.body);
        messages = [...messages, message];
        await new Promise((r) => setTimeout(r, 50));
        scrollDown();
    }

    async function sendMessage() {
        if (files.length > 0) {
            await sendImages();
        }
        var messageContent = message.trim();
        if (messageContent && stompClient) {
            var chatMessage = {
                senderUsername: username,
                recipientUsername: data.username,
                content: messageContent,
            };
            stompClient.send(`/app/chat`, {}, JSON.stringify(chatMessage));
            await new Promise((r) => setTimeout(r, 50));
            scrollDown();
        }
        message = "";
        files = [];
    }

    async function fileToByteArray(file: File) {
        return new Promise((resolve, reject) => {
            try {
                let reader = new FileReader();
                let fileByteArray: number[] = [];
                reader.readAsArrayBuffer(file);
                reader.onloadend = (evt: ProgressEvent) => {
                    if (evt.target?.readyState == FileReader.DONE) {
                        let arrayBuffer = evt.target.result,
                            array = new Uint8Array(arrayBuffer);
                        for (const byte of array) {
                            fileByteArray.push(byte);
                        }
                    }
                    resolve(fileByteArray);
                };
            } catch (e) {
                reject(e);
            }
        });
    }

    async function sendImages() {
        files.forEach(async (e) => {
            if (stompClient) {
                const lastIndex = getNameByPath(e.name).lastIndexOf(".");
                var chatImage = {
                    senderUsername: username,
                    recipientUsername: data.username,
                    image: await fileToByteArray(e),
                    fileExtension: e.name.slice(lastIndex + 1, e.name.length),
                };
                stompClient.send(
                    `/app/chat/img`,
                    {},
                    JSON.stringify(chatImage)
                );
            }
        });
    }

    function scrollDown() {
        const messagesDiv = document.querySelector(".messages");
        if (messagesDiv) {
            messagesDiv.scrollTo(0, messagesDiv.scrollHeight);
        }
    }

    async function loadMoreMessages() {
        if (data.messages.totalPages === 0 || apiPage === data.messages.totalPages - 1) return
        apiPage += 1;
        const req = await fetchHttp(`/messages/${username}/${data.username}?page=${apiPage}`, {
            token: get(tokenStore),
        });
        if (!req.ok) {
            alertStore.update(a => {
                a.color = "red"
                a.message = "Could not load more messages"
                return a
            })
            return
        }
        messages =  [...req.body.content.reverse(), ...messages]
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
            {#if data.messages.totalPages === 0 || apiPage === data.messages.totalPages - 1}
                <div id="uInfo" class="column">
                    <Avatar username={data.username} margin="0" size="85px" />
                    <a href="/{data.username}">
                        <p>{data.username}</p>
                    </a>
                </div>
            {/if}
            {#if data.messages.totalPages !== 0 && apiPage !== data.messages.totalPages - 1}
                {#if auto}
                    <Observer onvisible={loadMoreMessages} />
                {:else}
                    <div style="margin: 20px auto; width: max-content">
                        <Button onClick={() => {auto = true}} text="Load more" outline={true} />
                    </div>
                {/if}
            {/if}
            {#each messages as message}
                <Message {message} {username} />
            {/each}
        </div>
        <MessageInput bind:value={message} bind:files {sendMessage} />
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
            height: calc(100vh - 195px);
            max-height: 720px;
            margin: 0 auto;
            justify-content: center;

            #uInfo {
                width: 200px;
                margin: 20px auto;

                p {
                    font-size: 22px;
                    font-family: Inter, sans-serif;
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

    @media screen and (width <= 800px) {
        #chatList {
            display: none;
        }

        .chat {
            width: 100% !important;
        }
    }
</style>
