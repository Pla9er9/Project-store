import type { UserDtoSimple } from "../user/UserDtoSimple";
import type { IssueCommentDto } from "./IssueCommentDto";

type IssueDto = {
    id: string
    createdBy: UserDtoSimple
    title: string
    description: string
    open: boolean
    created: string
    comments: IssueCommentDto[]
};

export type { IssueDto };
