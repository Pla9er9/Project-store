import fetchHttp from "$lib/fetchHttp";
import type { ProjectDTO } from "$lib/models/project/ProjectDTO.js";
import { redirect } from "@sveltejs/kit";

export async function load({ params, cookies }) {
    const res = await fetchHttp(`/project/${params.id}`, {
        token: cookies.get("jwtToken"),
        server: true,
        showAlerts: false
    });
    const project_data: ProjectDTO = res.body;
    if (res.ok) {
        return {
            project_data: project_data,
        };
    } else {
        throw redirect(303, "/404")
    }
}
