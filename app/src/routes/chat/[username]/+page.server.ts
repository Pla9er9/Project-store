import { PUBLIC_API_URL } from '$env/static/public';
import { redirect } from '@sveltejs/kit';

export async function load({ params, cookies }) {
	const token = cookies.get("jwtToken")
	if (token === undefined) {
		throw redirect(300, "login")
	}
	const username = JSON.parse(atob(token.split('.')[1])).sub
	const req = await fetch(`${PUBLIC_API_URL}/messages/${username}/${params.username}`, {
		headers: {
			Authorization: 'Bearer ' + cookies.get('jwtToken')
		}
	});
	let messages = [];
	if (req.status === 404) {
		throw redirect(300, "/404")
	}
	else if (req.status == 200) { 
		messages = await req.json();
	}
	return {
		messages: messages,
		username: params.username
	};
}
