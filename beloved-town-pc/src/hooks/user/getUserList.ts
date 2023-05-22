import {onMounted} from "vue";
import {getUserListApi} from "@/api/api";
import {userStore} from "@/pinia/user";
import {userTableInfo} from "@/hooks/user/userConfig";


export const search = function () {
    getUserListHook()
}

export const getUserListHook = function () {
    const {userParam} = userStore();
    const userPina = userStore();
    userPina.setLoading(true);
    getUserListApi(userParam)
        .then((data: any) => {
            if (data.data && data.data.list) {
                // userPina.setLoading(false);
                // userPina.setUserList(data.data.list);
                // userPina.setTotal(data.data.total);
                userTableInfo.tableList = data.data.list;
                userTableInfo.pageInfo.total = data.data.total;
                userTableInfo.loading = false;
            }
        })
        .catch(error => {
            console.log('--=>user.error', error);
        });
};


export const handleSizeChange = (pageSize: number) => {
    const {userParam} = userStore();
    userParam.pageSize = pageSize;
    userParam.pageNo = 1;
    return getUserListHook();
}

export const handleCurrentChange = (pageNo: number) => {
    const {userParam} = userStore();
    userParam.pageNo = pageNo;
    getUserListHook()
}



