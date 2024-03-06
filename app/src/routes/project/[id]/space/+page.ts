import fetchHttp from '$lib/fetchHttp.js';
import { spaceStore } from '$lib/stores/spaceStore';
import { tokenStore } from '$lib/stores/tokenStore.js';
import { redirect } from '@sveltejs/kit';
import { get } from 'svelte/store';

export async function load({ params }) {
	const res = await fetchHttp(`/project/${params.id}/files`, {
		token: get(tokenStore),
		showAlerts: false,
	});

	if (!res.ok) {
		throw redirect(301, '/404');
	}

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
