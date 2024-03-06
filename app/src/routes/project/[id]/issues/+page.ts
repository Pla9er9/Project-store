import fetchHttp from '$lib/fetchHttp.js';
import { tokenStore } from '$lib/stores/tokenStore.js';
import { redirect } from '@sveltejs/kit';
import { get } from 'svelte/store';

export async function load({ params }) {
	async function loadData() {
		const res = await fetchHttp('/project/' + params.id + '/issues', {
			token: get(tokenStore),
			showAlerts: false
		});

		if (!res.ok) {
			throw redirect(301, '/404');
		}

		return res.body;
	}

	const data = await loadData();

	return {
		slug: params.id,
		data: data
	};
}
