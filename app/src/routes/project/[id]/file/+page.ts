import fetchHttp from '$lib/fetchHttp.js';
import { tokenStore } from '$lib/stores/tokenStore.js';
import { redirect } from '@sveltejs/kit';
import { get } from 'svelte/store';

export async function load({ params, url }) {
	const filePath = url.searchParams.get('path');

	if (filePath === null) {
		throw redirect(301, '/404');
	}

	async function loadData() {
		const res = await fetchHttp('/project/' + params.id + '/files?path=/' + encodeURIComponent(filePath ? filePath : ''), {
			token: get(tokenStore),
			showAlerts: false,
		});
		if (res.status == 404) {
			throw redirect(301, '/404');
		}

		if (!res.ok) {
			throw redirect(301, '/404');
		}

		return {
			slug: params.id,
			data: res.body
		};
	}

	return await loadData();
}
