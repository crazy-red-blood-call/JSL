import * as url from './url'
import {POST, POST_SINGLE} from './index'
import {ILoginForm} from '@/types/user/loginForm'
import {IUpdateLockParam, IUserInfo, IUserParam} from "@/types/user/userInfo";
import {ADD_MANICURE_URL, DELETE_MANICURE_URL, UPDATE_MANICURE_URL} from "./url";

// 登录接口
export const loginApi = function (param: ILoginForm) {
    return POST(url.LOGIN, param)
};

export const getUserListApi = function (param: IUserParam) {
    return POST(url.GET_USER_LIST, param)
};

export const deleteUserApi = function (param: string[]) {
    return POST(url.DELETE_USER, param)
};
export const addUserApi = function (param: IUserInfo) {
    return POST(url.ADD_USER, param)
};

export const updateUserLockApi = function (param: IUpdateLockParam) {
    return POST(url.UPDATE_USER_LOCK, param)
};

export const resetPasswordApi = function (param: string) {
    return POST_SINGLE(url.RESET_USER_PASSWORD, param)
};

export const getSystemRoleListApi = function (param: any) {
    return POST(url.ROLE_LIST_URL, param)
};

export const addRoleUrlApi = function (param: any) {
    return POST(url.ADD_ROLE_URL, param)
};
export const updateRoleUrlApi = function (param: any) {
    return POST(url.UPDATE_ROLE_URL, param)
};
export const deleteRoleApi = function (param: any) {
    return POST(url.DELETE_ROLE_URL, param)
};

export const updateRoleUserApi = function (param: any) {
    return POST(url.UPDATE_ROLE_USER_URL, param)
};
export const getManicureListApi = function (param: any) {
    return POST(url.GET_MANICURE_LIST_URL, param)
};

export const addManicureApi = function (param: any) {
    return POST(url.ADD_MANICURE_URL, param)
};

export const updateManicureApi = function (param: any) {
    return POST(url.UPDATE_MANICURE_URL, param)
};

export const deleteManicureApi = function (param: any) {
    return POST(url.DELETE_MANICURE_URL, param)
};
