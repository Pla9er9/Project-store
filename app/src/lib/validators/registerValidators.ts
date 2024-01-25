function validateEmail(email: string): string {
	email = email.trim();

	const re = new RegExp(`^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$`);
	if (!re.test(email)) {
		return 'Wrong email';
	}
	if (email.length > 320) {
		return 'Email max length is 320';
	}
	return '';
}

function validatePassword(password: string): string {
	password = password.trim();
	if (password.indexOf(' ') >= 0) {
		return 'Password should not contains space';
	}
	if (password.length < 8 || password.length > 50) {
		return 'Length should be beetwen 8 and 50 characters';
	}
	return '';
}

function validateUsername(username: string): string {
	username = username.trim();
	const re = new RegExp('^[a-zA-Z0-9]*$');
	if (username.length < 1 || username.length > 30) {
		return 'Length should be beetwen 1 and 30 characters';
	}
	if (!re.test(username)) {
		return 'Username should contains only letters and numbers';
	}
	return '';
}

function validateName(name: string, prefix: string): string {
	name = name.trim();
	const re = new RegExp('^[a-zA-Z]+$');
	if (name.length < 1 || name.length > 30) {
		return 'Length should be beetwen 1 and 30 characters';
	}
	if (!re.test(name)) {
		return prefix + 'name should contains only letters and numbers';
	}
	return '';
}

function validateFirstname(firstname: string): string {
	return validateName(firstname, 'First');
}

function validateLastname(lastname: string): string {
	return validateName(lastname, 'Last');
}

export { validateEmail, validatePassword, validateUsername, validateFirstname, validateLastname };
