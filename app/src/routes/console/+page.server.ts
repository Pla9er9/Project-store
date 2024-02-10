import fetchHttp from '$lib/fetchHttp';
import { tokenStore } from '$lib/stores/tokenStore';
import { get } from 'svelte/store';

export async function load({ params, cookies }) {
	const res = await fetchHttp("/dev/application", {
        token: get(tokenStore),
        redirecting: true,
        server: true
    })

	return {
		slug: params.id,
		data: res?.body
	};
}
