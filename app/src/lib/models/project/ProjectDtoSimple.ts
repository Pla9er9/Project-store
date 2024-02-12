import type { UserDtoSimple } from '../user/UserDtoSimple';
import type { Language } from './Language';

type ProjectDtoSimple = {
	id: string;
	name: string;
	likes: number;
	created: string;
	owner: UserDtoSimple;
	isPrivate: boolean;
	mainLanguage: Language;
};

export type { ProjectDtoSimple };
