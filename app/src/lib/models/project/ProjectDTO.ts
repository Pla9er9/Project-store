import type { UserDtoSimple } from '../user/UserDtoSimple';

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
	isPrivate: boolean;
	latestReleaseVersion: string;
	languages: string[];
	tags: string[];
};

export type { ProjectDTO };
