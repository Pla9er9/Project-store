import { redirect } from '@sveltejs/kit';
import type { LayoutServerLoad } from './$types';

export const load: LayoutServerLoad = async ({ cookies, route }) => {
	const token = cookies.get('jwtToken');

	if (!token) {
		return {
			username: null
		};
	}
	if (route.id?.toString() === "" && token) {
		throw redirect(300, "/home")
	}
	const username: string = JSON.parse(atob(token.split('.')[1])).sub;
	return {
		username: username,
		token: token
	};
};
