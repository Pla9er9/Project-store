import type { UserDtoSimple } from '../user/UserDtoSimple';

type IssueCommentDto = {
	createdBy: UserDtoSimple;
	comment: string;
	created: string;
};

export type { IssueCommentDto };
