import type { UserDtoSimple } from "../user/UserDtoSimple"

type IssueDtoSimple = {
    id: string
    createdBy: UserDtoSimple
    title: string
    isOpen: boolean
    projectId: string
    created: string
}

export type {IssueDtoSimple}