import fetchHttp from "$lib/fetchHttp"

export const load = async () => {
    return {
        data: await fetchHttp(`/project/trending`, {})
    }
}