import fetchHttp from '$lib/fetchHttp.js'

export async function load({ url }) {
    const query = url.searchParams.get("q")
    const type = url.searchParams.get("t")
    
    let _type = ""
    if (type === "users") {
        _type = "users/"
    } else if (type === "project-name") {
        _type = "projects/"
    }
    const res = await fetchHttp(`/search/${_type}${query}`, {})
    

    return {
        query: query,
        type: type,
        res: res?.body
    }
}