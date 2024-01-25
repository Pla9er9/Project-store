import type { ProjectDtoSimple } from "../project/ProjectDtoSimple";

type UserDto = {
    userId:string;
    username:string;
    description:string;
    firstname:string;
    lastname :string;
    isFollowed: boolean;
    followers: number;
    following: number;
    personalLinks: string[];
    projects: ProjectDtoSimple[];
}
export type {UserDto}