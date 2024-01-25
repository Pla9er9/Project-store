import fetchHttp from "$lib/fetchHttp"

export const load = async () => {
    return await fetchHttp(`/project/trending`, {})
}