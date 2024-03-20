import type { Actions } from "./$types";
import { PUBLIC_API_URL } from "$env/static/public";
import { error } from "@sveltejs/kit";

export const actions: Actions = {
    default: async ({ cookies, request }) => {
        const data = await request.json();
        const res = await fetch(PUBLIC_API_URL + "/auth/signup", {
            method: "POST",
            body: JSON.stringify(data),
            headers: {
                "Content-type": "application/json;charset=UTF-8",
            },
        });

        const json = await res.json();

        if (!res.ok) {
            throw error(res.status, json.message);
        }

        cookies.set("jwtToken", json.token, {
            path: "/",
            httpOnly: true,
            sameSite: "strict",
            secure: process.env.NODE_ENV === "production",
            maxAge: 60 * 60 * 24 * 365 * 10,
        });
    },
};
