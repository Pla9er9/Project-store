import type { Actions } from '@sveltejs/kit';
import fetchHttp from '$lib/fetchHttp';

export const actions: Actions = {
	default: async ({ request, cookies }) => {
		const data = await request.json();
		const response = await fetchHttp("/auth/signin", {
			method: 'POST',
			body: data,
			server: true
		})
		if (!response?.body) {
			return
		}
		cookies.set('jwtToken', response.body.token, {
			path: '/',
			httpOnly: true,
			sameSite: 'strict',
			secure: process.env.NODE_ENV === 'production',
			maxAge: 60 * 60 * 24 * 365 * 10
		});
		return { status: 200 };
	}
};
