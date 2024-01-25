import type { Actions } from './$types';
import fetchHttp from '$lib/fetchHttp';

export const actions: Actions = {
	default: async ({ cookies, request }) => {
		const data = await request.json();
		const res = await fetchHttp("/auth/signup", {
			method: 'POST',
			body: data,
		})

		cookies.set('jwtToken', res?.body.token, {
			path: '/',
			httpOnly: true,
			sameSite: 'strict',
			secure: process.env.NODE_ENV === 'production',
			maxAge: 60 * 60 * 24 * 365 * 10
		});
		return { status: 200 };
	}
};
