import fetchHttp from "$lib/fetchHttp";
import { compile } from "mdsvex";

export async function load({ params }) {
    async function loadReadme() {
        const res = await fetchHttp(
            `/project/${params.id}/files?path=readme.md`,
            {}
        );

        if (res?.ok) {
            return res.body;
        } else {
            return "";
        }
    }

    let readme = await loadReadme();
    readme = await toMarkdown(readme);

    return {
        slug: params.id,
        readme: readme,
    };
}

async function toMarkdown(source: string) {
    const compiled = await compile(source);
    return compiled ? compiled?.code : "";
}
