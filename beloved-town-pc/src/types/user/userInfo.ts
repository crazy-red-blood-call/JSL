export interface IUserParam {
    userId?: string,
    accountName?: string,
    userName?: string,
    lockFlag?: number,
    pageNo: number,
    pageSize: number
}


export interface IUserInfo {
    userId?: string,
    accountName?: string,
    userName?: string,
    phone?: string,
    gender?: string,
    lockFlag?: string,
    createTime?: string,
    updateTime?: string,
}

export class UserInfo {
    userList: IUserInfo[] = [];
}

export interface IUpdateLockParam {
    userIdList: string[],
    lockFlag: number
}