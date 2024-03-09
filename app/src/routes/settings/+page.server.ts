import { PUBLIC_API_URL } from "$env/static/public";

export const load = async ({ cookies }) => {
    const token = cookies.get("jwtToken");
    if (!token) {
        return;
    }

    async function loadData() {
        const res = await fetch(PUBLIC_API_URL + "/account", {
            headers: {
                Authorization: "Bearer " + token,
            },
        });
        if (!res.ok) {
            return {
                data: undefined,
            };
        }
        return await res.json();
    }

    return {
        data: await loadData(),
    };
};
