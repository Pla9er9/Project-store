import fetchHttp from '$lib/fetchHttp.js'

export async function load({ cookies }) {
    const res = await fetchHttp("/account/invitations", {
        token: cookies.get("jwtToken"),
        server: true
    })
    return {
        data: res.body
    }
}