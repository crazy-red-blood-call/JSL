import {defineStore} from "pinia";
import {IUserInfo, IUserParam} from "@/types/user/userInfo";

export const userStore = defineStore('userStore', {
    state: () => {
        return {
            addUserVisible: false,
            userList: [] as IUserInfo[],
            total: 0,
            loading: false,
            userParam: {pageNo: 1, pageSize: 10} as IUserParam,
            selectedUserList: [] as any[],
        }
    },
    getters: {
        getAddUserVisible(): boolean {
            return this.addUserVisible;
        },
        getUserList(): IUserInfo[] {
            return this.userList;
        },
        getTotal(): number {
            return this.total;
        },
        getSelectedUserList(): any[] {
            return this.selectedUserList;
        },
        getUserParam(): IUserParam {
            return this.userParam;
        }
    },
    actions: {
        setAddUserVisible(value: boolean) {
            this.addUserVisible = value;
        },
        setUserList(value: any[]) {
            this.userList = value;
        },
        setTotal(value: number) {
            this.total = value;
        },
        setSelectedUserList(value: any[]): void {
            this.selectedUserList = value;
        },
        setUserParam(value: IUserParam): void {
            this.userParam = value;
        },
        setLoading(value: boolean): void {
            this.loading = value;
        }
    }
});
