import fetchHttp from '$lib/fetchHttp.js';
import { spaceStore } from '$lib/stores/spaceStore';

export async function load({ params }) {
	const res = await fetchHttp(`/project/${params.id}/files`, {});

	spaceStore.update((v) => {
		v.projectId = params.id;
		return v;
	});

	return {
		folders: res?.body.folders,
		files: res?.body.files,
	};
}
