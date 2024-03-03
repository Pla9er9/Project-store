import { PUBLIC_API_URL } from '$env/static/public';
import { redirect } from '@sveltejs/kit';

export async function load({ params, url }) {
	const filePath = url.searchParams.get('path');

	if (filePath === null) {
		throw redirect(301, '/404');
	}

	async function loadData() {
		const res = await fetch(PUBLIC_API_URL + '/project/' + params.id + '/files?path=/' + encodeURIComponent(filePath ? filePath : ''));
		if (res.status == 404) {
			throw redirect(301, '/404');
		}

		if (!res.ok) {
			return {
				slug: params.id,
				data: undefined
			};
		}
		const data = await res.text();
		let copy = undefined;
		try {
			copy = JSON.parse(data);
		} catch {
			copy = data;
		}

		return {
			slug: params.id,
			data: copy
		};
	}

	return await loadData();
}
