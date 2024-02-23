import { writable, type Writable } from 'svelte/store';

type Space = {
	projectId: string;
	currentFile: string;
	loadedFiles: Map<string, string>;
	editedFiles: Map<string, string>;
};

const defaultOpt: Space = {
	projectId: '',
	currentFile: '',
	loadedFiles: new Map<string, string>(),
	editedFiles: new Map<string, string>()
};

export const spaceStore: Writable<Space> = writable(defaultOpt);
export type { Space };
