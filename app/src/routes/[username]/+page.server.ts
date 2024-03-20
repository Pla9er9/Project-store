import fetchHttp from '$lib/fetchHttp.js';
import type { UserDto } from '$lib/models/user/UserDto.js';
import DOMPurify from "isomorphic-dompurify";

export async function load({ params, cookies }) {
	async function loadData() {
		return await fetchHttp(`/user/${params.username}`, {
			token: cookies.get("jwtToken"),
			redirecting: true,
			server: true,
			showAlerts: false,
		})
	}
	const res = await loadData()
	const resBody: UserDto = res?.body

	resBody.description = DOMPurify.sanitize(resBody.description)

	return {
		slug: params.username,
		data: resBody
	};
}
