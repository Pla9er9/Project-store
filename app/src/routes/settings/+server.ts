import fetchHttp from "$lib/fetchHttp.js";
import { RequestHandler } from "@sveltejs/kit";

export const POST: RequestHandler = async ({request, cookies}) => {
		const data = await request.json();
		const res = await fetchHttp("/account", {
			method: "put",
			body: JSON.stringify(data),
			token: cookies.get("jwtToken"),
		});

		if (!res.ok || res.body === "") {
			return new Response("", { status: res.status }); 
		}

		cookies.set("jwtToken", res?.body, {
			path: "/",
			httpOnly: true,
			sameSite: "strict",
			secure: process.env.NODE_ENV === "production",
			maxAge: 60 * 60 * 24 * 365 * 10,
		});
        
		return new Response("", { status: 200 }); 
}