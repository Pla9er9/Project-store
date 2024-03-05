import type { UserDtoSimple } from '../user/UserDtoSimple';
import type { Language } from './Language';

type ProjectDTO = {
	id: string;
	name: string;
	description: string;
	license: string;
	likes: number;
	liked: boolean;
	created: string;
	codeSize: string;
	owner: UserDtoSimple;
	creators: UserDtoSimple[];
	private: boolean;
	latestReleaseVersion: string;
	languages: Language[];
	tags: string[];
};

export type { ProjectDTO };
