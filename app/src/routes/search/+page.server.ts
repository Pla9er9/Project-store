import fetchHttp from "$lib/fetchHttp.js";

export async function load({ url }) {
    const query = url.searchParams.get("q");
    const type = url.searchParams.get("t");

    let endpoint = "";
    if (type === "users") {
        endpoint = "users/";
    } else if (type === "project-name") {
        endpoint = "projects/";
    } else if (type === "project-tag") {
        endpoint = "tags/"
    }

    const res = await fetchHttp(`/search/${endpoint}${query}`, {});
    return {
        query: query,
        type: type,
        res: res?.body,
    };
}
