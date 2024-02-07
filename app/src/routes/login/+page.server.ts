import type { Actions } from '@sveltejs/kit';
import fetchHttp from '$lib/fetchHttp';

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
		if (!response?.body) {
			return { status: 404 }
		}
		event.cookies.set('jwtToken', response.body.token, {
			path: '/',
			httpOnly: true,
			sameSite: 'strict',
			secure: process.env.NODE_ENV === 'production',
			maxAge: 60 * 60 * 24 * 365 * 10
		});
		return { status: 200 };
	}
};
