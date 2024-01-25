<script>
	import fetchHttp from "$lib/fetchHttp";
	import { tokenStore } from "$lib/stores/tokenStore";
	import { createEventDispatcher } from "svelte";
	import { get } from "svelte/store";

	export let invitation;

    var dispatch = createEventDispatcher()

	async function acceptInvitation() {
        let res = await fetchHttp(`/invitation/${invitation.id}/accept`, {
            token: get(tokenStore)
        })
        if (res != undefined && res.status === 200) {
            deleteFromList()
        }
    }
    
	async function rejectInvitation() {
        let res = await fetchHttp(`/invitation/${invitation.id}`, {
            method: 'delete',
            token: get(tokenStore)
        })
        if (res != undefined && res.status === 200) {
            deleteFromList()
        }
    }

    function deleteFromList() {
        dispatch('deleteFromList', {
            id: invitation.id
        })
    }

</script>

<div class="invitation">
	<img src="/icons/squares.svg" alt="" />
	<p>{invitation.projectName}</p>
	<small>{invitation.created.slice(0, 10)}</small>
	<button on:click={acceptInvitation}>
		<img src="/icons/checkmark.svg" alt="" />
	</button>
	<button on:click={rejectInvitation}>
		<img src="/icons/redDelete.svg" alt="" />
	</button>
</div>

<style lang="scss">
	.invitation {
		margin-left: 30px;
		margin: 10px 0;
		border-radius: 5px;
		width: 100vw;
		max-width: 550px;
		height: 45px;
		border: solid 1px #474747;
		display: flex;
		align-items: center;

		img {
			width: 25px;
			height: 25px;
			margin-left: 8px;
		}

		p {
			color: #e6e3e3;
			text-indent: 15px;
			width: 60%;
			overflow-x: auto;
		}

		small {
			margin-top: 5px;
			margin-left: auto;
			color: rgb(110, 110, 110);
			font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
		}

		button {
			width: 45px;
			height: 45px;
			display: flex;
			justify-content: center;
			background-color: inherit;
			align-items: center;
			border: none;
			border-radius: 10px;
			cursor: pointer;

			&:first-of-type {
				margin-left: auto;

				&:hover {
					background-color: #93f84f27;
				}
			}

			&:last-of-type {
				margin-right: 4px;

				&:hover {
					background-color: #f84f4f27;
				}
			}

			img {
				margin-left: 0;
				width: 22px;
				height: 22px;
			}
		}
	}
</style>
