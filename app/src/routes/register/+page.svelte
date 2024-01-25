<script lang="ts">
	import { goto } from '$app/navigation';
	import Input from '$components/forms/Input.svelte';
	import SubmitButton from '$components/forms/SubmitButton.svelte';
	import * as validators from '$lib/validators/registerValidators';

	let username = '',
		usernameValidated = false,
		email = '',
		emailValidated = false,
		password = '',
		passwordValidated = false,
		firstname = '',
		firstnameValidated = false,
		lastname = '',
		lastnameValidated = false;

	$: isFormValid =
		usernameValidated &&
		emailValidated &&
		passwordValidated &&
		firstnameValidated &&
		lastnameValidated;

	async function sendForm() {
		if (!isFormValid) return;
		let res = await fetch('http://localhost:5173/register', {
			method: 'post',
			body: JSON.stringify({
				username: username,
				email: email,
				password: password,
				firstname: firstname,
				lastname: lastname
			})
		});
		if (res.ok) {
			await goto('/');
			location.reload()
		}
	}
</script>

<main>
	<img src="/icons/user_.svg" alt="">
	<h1>Registration</h1>
	<div class="form">
		<Input
			placeholder="username"
			validator={validators.validateUsername}
			bind:value={username}
			bind:correct={usernameValidated}
		>
			<img src="/icons/user_outline.svg" alt="">
		</Input>
		<Input
			placeholder="email"
			maxlength={320}
			validator={validators.validateEmail}
			bind:value={email}
			bind:correct={emailValidated}
		>
			<img src="/icons/email_outline.svg" alt="">
		</Input>
		<Input
			placeholder="password"
			validator={validators.validatePassword}
			bind:value={password}
			bind:correct={passwordValidated}
			maxlength={50}
			password={true}
		>
			<img src="/icons/password_outline.svg" alt="">
		</Input>
		<Input
			placeholder="firstname"
			validator={validators.validateFirstname}
			bind:value={firstname}
			bind:correct={firstnameValidated}
		>
			<img src="/icons/personal_data_outline.svg" alt="">
		</Input>
		<Input
			placeholder="lastname"
			validator={validators.validateLastname}
			bind:value={lastname}
			bind:correct={lastnameValidated}
		>
			<img src="/icons/personal_data_outline.svg" alt="">
		</Input>
		<SubmitButton text="Register" isValid={isFormValid} callback={async () => await sendForm()}>
			<img src="/icons/door_open.svg" alt="">
		</SubmitButton>
	</div>
</main>

<style lang="scss">
	main {
		max-width: 1000px;
		width: 100%;
		margin: 60px auto;
		display: flex;
		flex-direction: column;
		align-items: center;

		h1 {
			background: -webkit-linear-gradient(#eee, #333);
			-webkit-background-clip: text;
			background-clip: text;
			-webkit-text-fill-color: transparent;
			margin-bottom: 50px;
		}

		img {
			margin: 0;
			width: 150px;
			height: 150px;
		}

		.form {
			max-width: 600px;
			width: 95%;
			display: flex;
			justify-content: space-around;
			flex-wrap: wrap;
			margin: 0 auto;

			img {
				width: 22px;
			}

		}
	}
</style>
