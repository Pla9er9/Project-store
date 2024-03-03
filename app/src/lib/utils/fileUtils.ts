import fetchHttp from "$lib/fetchHttp";
import { alertStore } from "$lib/stores/alertStore";
import { spaceStore } from "$lib/stores/spaceStore";
import { tokenStore } from "$lib/stores/tokenStore";
import { get } from "svelte/store";

export async function addNewByPath(
    content: string,
    newPath: string
): Promise<boolean> {
    const filename = getNameByPath(newPath);
    const formData = new FormData();
    const blob = new Blob([content], { type: "text/plain" });
    const file = new File([blob], filename, { type: "text/plain" });
    formData.append("file", file);

    const res = await fetchHttp(
        `/project/${getProjectId()}/files?path=${encodeURIComponent(newPath)}`,
        {
            method: "POST",
            token: get(tokenStore),
            stringify: false,
            noContentType: true,
            body: formData,
        }
    );
    if (!res?.ok) {
        alertStore.update((a) => {
            a.color = "red";
            a.message = "Could not upload file";
            return a;
        });
        return false;
    }

    spaceStore.update((s) => {
        s.editedFiles.set(newPath, content);
        s.loadedFiles.set(newPath, content);
        return s;
    });
    return true;
}

export async function changeName(
    newName: string,
    path: string
): Promise<boolean> {
    const name = getNameByPath(path);
    if (!newName) return false;

    if (newName === name) {
        fileExistAlert();
    }

    const newPath = path.slice(0, path.length - name.length) + newName;

    const res = await fetchHttp(
        `/project/${getProjectId()}/files?path=${encodeURIComponent(newPath)}`,
        {}
    );

    if (res?.status === 200) {
        fileExistAlert();
        return false;
    }

    if (res?.status !== 404) return false;

    const content = get(spaceStore).editedFiles.get(path);

    if (!content) return false;

    let succes = deleteByPath(path);
    if (!succes) {
        return false;
    }
    succes = addNewByPath(content, newPath);
    if (!succes) {
        return false;
    }
    return true;
}

export async function deleteByPath(path: string): Promise<boolean> {
    const res = await fetchHttp(
        `/project/${getProjectId()}/files?path=${encodeURIComponent(path)}`,
        {
            method: "DELETE",
            token: get(tokenStore),
        }
    );
    if (!res?.ok) {
        alertStore.update((a) => {
            a.color = "red";
            a.message = "Could not delete file";
            return a;
        });
        return false;
    }

    spaceStore.update((s) => {
        if (s.currentFile === path) {
            s.currentFile = "";
        }
        s.editedFiles.delete(path);
        s.loadedFiles.delete(path);
        return s;
    });
    return true;
}

export function getNameByPath(path: string): string {
    let res = "";
    for (let i = path.length - 1; i >= 0; i--) {
        if (path[i] == "/") {
            return res;
        } else {
            res = path[i] + res;
        }
    }
    return res;
}

export function fileExistAlert() {
    alertStore.update((a) => {
        a.color = "yellow";
        a.message = "This file already exist";
        return a;
    });
}

export function getProjectId(): string {
    const errorText = "Get project id used without correct route";
    let idFromUrl: string | null = null;

    if (window) {
        if (location.href.length < 66) {
            console.log(errorText);
            return "";
        }
        const p = "project";
        const i = location.href.indexOf(p) + p.length;
        idFromUrl = location.href.slice(i + 1, i + 37);
    }
    const idFromSpace = get(spaceStore).projectId;

    if (idFromSpace !== "") {
        if (idFromUrl && idFromUrl === idFromSpace) {
            return idFromSpace;
        } else if (idFromUrl) {
            return idFromUrl;
        }
        console.log("Get project id used without correct route");
        return "";
    } else {
        if (!idFromUrl) {
            console.log("Get project id used without correct route");
            return "";
        } else {
            return idFromUrl;
        }
    }
}
