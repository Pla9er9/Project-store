import { redirect } from '@sveltejs/kit';
import type { LayoutServerLoad } from './$types';

export const load: LayoutServerLoad = async ({ cookies, route, url }) => {
	const token = cookies.get('jwtToken');

	const oauthId = url.searchParams.get("client_id")
	const redirectUrl = url.searchParams.get("redirectUrl")
	
	if (!token) {
		return {
			username: null,
			hideUiParts: oauthId !== null && redirectUrl !== null
		};
	}
	if (route.id?.toString() === "" && token) {
		throw redirect(300, "/home")
	}
	const username: string = JSON.parse(atob(token.split('.')[1])).sub;
	return {
		username: username,
		token: token,
		hideUiParts: oauthId !== null && redirectUrl !== null
	};
};
