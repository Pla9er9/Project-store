import { PUBLIC_API_URL } from '$env/static/public';
import fetchHttp from '$lib/fetchHttp';
import type { InvitationDto } from '$lib/models/invitation/InvitationDto.js';
import type { ProjectDTO } from '$lib/models/project/ProjectDTO.js';
import { redirect } from '@sveltejs/kit';
import { compile } from 'mdsvex';

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

	async function loadCode() {
		const req = await fetch(PUBLIC_API_URL + '/project/' + params.id + '/files');

		if (req.status == 404) {
			return {
				files: [],
				folders: []
			};
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

	async function loadInvitations(): Promise<InvitationDto[] | undefined>  {
		const token = cookies.get('jwtToken')
		if (token === undefined) {
			return undefined
		}
		const res = await fetchHttp(`/project/${params.id}/invitations`, {
			token: token,
			server: true,
			redirecting: true
		})
		if (!res) {
			return []
		}
		return res.body
	}

	const data = await loadData();
	if (!data) {
		throw redirect(300, '404')
	}
	const files = await loadCode();
	const invitations = await loadInvitations();
	const readme = await toMarkdown(``);

	return {
		slug: params.id,
		data: {
			info: data,
			files: files,
			readme: readme,
			invitations: invitations
		}
	};
}

async function toMarkdown(source: string) {
	const compiled = await compile(source);
	return compiled ? compiled?.code : '';
}
