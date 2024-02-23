import fetchHttp from '$lib/fetchHttp.js';
import { spaceStore } from '$lib/stores/spaceStore';

export async function load({ params }) {
	const res = await fetchHttp(`/project/${params.id}/files`, {
		redirecting: true
	});

	spaceStore.set({
		projectId: params.id,
		currentFile: '',
		loadedFiles: new Map<string, string>(),
		editedFiles: new Map<string, string>()
	})

	return {
		folders: res?.body.folders,
		files: res?.body.files,
	};
}
