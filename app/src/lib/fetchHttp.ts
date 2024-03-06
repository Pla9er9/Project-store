import { goto } from '$app/navigation';
import { redirect } from '@sveltejs/kit';
import { alertStore } from './stores/alertStore';
import { PUBLIC_API_URL } from '$env/static/public';

let any: any

export default async function fetchHttp(
	url: string,
    {
        method = 'GET',
        body = any,
        token = '',
        apiUrlPrefix = true,
        server = false,
		showAlerts = true,
		stringify = true,
		noContentType = false,
		redirecting = false,
        contentType = 'application/json;charset=UTF-8',
        errorMessage = 'Some unexpected error occurred',
		headers = {}
    }
// eslint-disable-next-line @typescript-eslint/no-explicit-any
): Promise<response> {
	let auth = '';
	let apiPrefix = '';
	let stringBody = '';

	if (apiUrlPrefix) apiPrefix = PUBLIC_API_URL;

	if (token !== '' && token !== undefined) auth = 'Bearer ' + token;

	if (url[0] !== '/' && apiUrlPrefix) url = '/' + url;

	if (body !== undefined && typeof body !== 'string') {
		stringBody = JSON.stringify(body);
	} else if (typeof body === 'string') {
		stringBody = body;
	}

	if (server) showAlerts = false

	let res
	if (noContentType) {
		res = await fetch(apiPrefix + url, {
			method: method,
			body: method === "GET" ? null : stringify ? stringBody : body,
			headers: {
				Authorization: auth,
				...headers
			},
			redirect: 'follow'
		});
	} else {
		res = await fetch(apiPrefix + url, {
			method: method,
			body: method === "GET" ? null : stringify ? stringBody : body,
			headers: {
				Authorization: auth,
				'Content-type': contentType,
				...headers
			},
			redirect: 'follow'
		});
	}
	if (res.redirected) {
		window.location.href = res.url;
	}
	if ((res.status === 403 || res.status === 401) && redirecting) {
		if (server) {
			throw redirect(303, '/login');
		}
		await goto('login');
		return {
			body: "",
			status: res.status,
			ok: false,
		};
	}
	if (res.status === 500 || res.status === 400) {
		if (showAlerts && !server) {
			alertStore.update((alertStore) => {
				alertStore.message = errorMessage;
				alertStore.color = 'red';
				return alertStore;
			});
		}
		return {
			body: "",
			status: res.status,
			ok: false,
		};
	}

	if (res.status === 404 && redirecting) {
		if (server) {
			throw redirect(303, '/404');
		} else {
			await goto('404');
			return {
				body: "",
				status: res.status,
				ok: false,
			};
		}
	}

	if (res.ok) {
		const b = await res.text()
		try {
			return {
				body: JSON.parse(b),
				status: res.status,
				ok: res.status === 200,
			};
		} catch {
			return {
				body: b,
				status: res.status,
				ok: res.status === 200,
			};
		}
	} else {
		return {
			body: "",
			status: res.status,
			ok: res.status === 200,
		};
	}
}

type response = {
	// eslint-disable-next-line @typescript-eslint/no-explicit-any
	body: any,
	status: number
	ok: boolean
}