import { writable, type Writable } from 'svelte/store';

type Alert = {
	message: string;
	color: string;
};

const defaultOpt: Alert = {
	message: '',
	color: 'blue'
};
export const alertStore: Writable<Alert> = writable(defaultOpt);
export type { Alert };
