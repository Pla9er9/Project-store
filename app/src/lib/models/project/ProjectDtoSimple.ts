import type { UserDtoSimple } from '../user/UserDtoSimple';

type ProjectDtoSimple = {
	id: string;
	name: string;
	likes: number;
	created: string;
	owner: UserDtoSimple;
	isPrivate: boolean;
	mainLanguage: string;
};

export type { ProjectDtoSimple };
