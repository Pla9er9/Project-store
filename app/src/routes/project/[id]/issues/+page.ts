import { PUBLIC_API_URL } from '$env/static/public';
import { redirect } from '@sveltejs/kit';

export async function load({ params }) {
	async function loadData() {
		const req = await fetch(PUBLIC_API_URL + '/project/' + params.id + '/issues');

		if (req.status == 404) {
			throw redirect(301, '/404');
		}

		if (!req.ok) {
			return {
				slug: params.id,
				data: undefined
			};
		}

		const data = await req.json();

		return data;
	}

	const data = await loadData();

	return {
		slug: params.id,
		data: data
	};
}
