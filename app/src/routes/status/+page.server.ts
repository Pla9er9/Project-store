import fetchHttp from "$lib/fetchHttp.js";

export async function load({ url }) {
    const res = await fetchHttp('/status', {});
    return {
        res: res?.body,
    };
}
