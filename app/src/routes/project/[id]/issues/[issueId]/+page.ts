import fetchHttp from '$lib/fetchHttp.js';
import { tokenStore } from '$lib/stores/tokenStore';
import { redirect } from '@sveltejs/kit';
import { get } from 'svelte/store';

export async function load({ params }) {
	const res = await fetchHttp(`project/${params.id}/issues/${params.issueId}`, {
		showAlerts: false,
		token: get(tokenStore)
	});

	if (!res.ok) {
		throw redirect(301, '/404');
	}

	return {
		slug: params.id,
		issueId: params.issueId,
		data: res.body
	};
}
