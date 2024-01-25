import fetchHttp from '$lib/fetchHttp.js';
import { redirect } from '@sveltejs/kit';

export async function load({ params }) {
	const data = await fetchHttp(`project/${params.id}/issues/${params.issueId}`, {
		server: true,
		showAlerts: false
	});

	if (data === undefined) {
		throw redirect(304, "")
	}

	return {
		slug: params.id,
		issueId: params.issueId,
		data: data.body
	};
}
