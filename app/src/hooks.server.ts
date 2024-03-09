import { redirect } from '@sveltejs/kit';

const securedRoutes = [
    "/settings", "/new", "/home", "/console", "/chat", "/logout"
]

const noAuthRoutes = [
    "/login", "/register"
]

export async function handle({ event, resolve }) {
    const token = event.cookies.get('jwtToken')
    const auth = authenticate(token) !== null
    const routes = auth ? noAuthRoutes : securedRoutes
    
    let isRouteNotAllowed = false
    
    for (const route of routes) {
        if (event.url.pathname.startsWith(route)) {
            isRouteNotAllowed = true
            break
        } 
    }
    if (isRouteNotAllowed) {
        throw redirect(303, `/${auth ? 'home' : 'login'}`)
    }

    return await resolve(event)
}

function authenticate(token: string | undefined): string | null {
    if (!token) return null

    try {
        const usernameFromToken = JSON.parse(atob(token.split(".")[1])).sub;
        return usernameFromToken
    } catch (_) {
        return null
    }
}
