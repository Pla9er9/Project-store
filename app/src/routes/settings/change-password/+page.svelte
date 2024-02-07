<script>
	import { validatePassword } from "$lib/validators/registerValidators";
	import Input from "$components/forms/Input.svelte";
	import SubmitButton from "$components/forms/SubmitButton.svelte";
	import fetchHttp from "$lib/fetchHttp";
	import { get } from "svelte/store";
	import { tokenStore } from "$lib/stores/tokenStore";

	async function resetPassword() {
		await fetchHttp("/account/change-password", {
			method: "put",
			body: JSON.stringify({
				password: input1_,
			}),
			token: get(tokenStore),
		});
	}

	let input1 = false;
	let input2 = false;
	let input1_ = "";
	let input2_ = "";

	$: isValid = input1 && input2 && input1_ === input2_;
</script>

<main>
	<h1 class="settingsHeader">Change password</h1>
	<br />
	<Input
		placeholder="New Password"
		bind:correct={input1}
		bind:value={input1_}
		validator={validatePassword}
	>
		<img src="/icons/password.svg" alt="password icon" />
	</Input>
	<Input
		placeholder="Repeat Password"
		bind:correct={input2}
		bind:value={input2_}
		validator={validatePassword}
	>
		<img src="/icons/password.svg" alt="password icon" />
	</Input>
	<SubmitButton
		text="Zresetuj"
		{isValid}
		callback={resetPassword}
		iconUrl="/icons/checkmark.svg"
	/>
</main>

<style>
	img {
		width: 20px;
	}
</style>
