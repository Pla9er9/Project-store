import { writable, type Writable } from 'svelte/store';

type Toast = {
    header: string
    description: string
    status: "none" | "loading"
};

const defaultOpt: Toast = {
	header: '',
    description: "",
	status: "none"
};

export const toastStore: Writable<Toast> = writable(defaultOpt);
export type { Toast };
