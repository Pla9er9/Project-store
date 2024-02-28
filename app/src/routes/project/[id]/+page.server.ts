import fetchHttp from '$lib/fetchHttp';
import type { ProjectDTO } from '$lib/models/project/ProjectDTO.js';
import { redirect } from '@sveltejs/kit';
import { compile } from "mdsvex";


export async function load({ params, cookies }) {
	async function loadData(): Promise<ProjectDTO | undefined> {
		const res = await fetchHttp(`/project/${params.id}`, {
			token: cookies.get('jwtToken'),
			server: true,
			redirecting: true
		})
		if (res?.ok) {
			return res.body
		}
	}

	async function loadReadme() {
		const res = await fetchHttp(
			`/project/${params.id}/files?path=readme.md`,
			{},
		);

		if (res?.ok) {
			return res.body
		} else {
			return ""
		}
	}

	const data = await loadData();
	let readme = await loadReadme()
	readme = await toMarkdown(readme);

	if (!data) {
		throw redirect(300, '404')
	}

	return {
		slug: params.id,
		data: {
			info: data,
			readme: readme,
		}
	};
}

async function toMarkdown(source: string) {
	const compiled = await compile(source);
	return compiled ? compiled?.code : '';
}
