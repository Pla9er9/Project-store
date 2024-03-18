import fetchHttp from "$lib/fetchHttp";
import { tokenStore } from "$lib/stores/tokenStore.js";
import { compile } from "mdsvex";
import { get } from "svelte/store";

export async function load({ params }) {
    async function loadReadme() {
        const res = await fetchHttp(
            `/project/${params.id}/files?path=readme.md`,
            {
                token: get(tokenStore),
                server: true
            }
        );
        return res
    }
    
    const res = await loadReadme();
    const md = await toMarkdown(res.body ? res.body : '');

    return {
        slug: params.id,
        readme: md,
    };
}

async function toMarkdown(source: string) {
    const compiled = await compile(source);
    return compiled ? compiled?.code : "";
}
