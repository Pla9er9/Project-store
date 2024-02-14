import { error, type Actions, redirect } from '@sveltejs/kit';
import fetchHttp from '$lib/fetchHttp';

export async function load({ url, cookies }) {
	const oauthId = url.searchParams.get("appId")
	const redirectUrl = url.searchParams.get("redirectUrl")
	return {
		oauthId: oauthId,
		redirectUrl: redirectUrl,
		isOauth2: oauthId !== null && redirectUrl !== null
	}
}

export const actions: Actions = {
	default: async (event) => {
		const data = await event.request.json();
		const response = await fetchHttp("/auth/signin", {
			method: 'POST',
			body: data,
			server: true,
			headers: {
				Addr: event.getClientAddress(),
				"User-Agent": event.request.headers.get("User-Agent")
			}
		})
		if (response === undefined) {
			throw error(404)
		}
		if (response?.status !== 200) {
			throw error(404)
		}
		event.cookies.set('jwtToken', response?.body.token, {
			path: '/',
			httpOnly: true,
			sameSite: 'strict',
			secure: process.env.NODE_ENV === 'production',
			maxAge: 60 * 60 * 24 * 365 * 10
		});
		return { status: 200 };
	}
};

