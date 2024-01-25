import { writable, type Writable } from 'svelte/store';

export const tokenStore: Writable<string | undefined> = writable(undefined);
