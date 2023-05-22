import {getUserListHook} from "./getUserList";
import {ElMessage, ElMessageBox} from "element-plus";
import {deleteUserApi} from "@/api/api";
import {userStore} from "@/pinia/user";

export const deleteBatch = function () {
    const userStores = userStore();
    let multipleSelection = userStores.getSelectedUserList;
    if (multipleSelection.length == 0) {
        ElMessage({
            message: '请勾选你要删除的数据',
            type: 'warning',
        });
    } else {
        ElMessageBox.confirm(
            '确认删除?',
            '删除用户',
            {
                confirmButtonText: '确认',
                cancelButtonText: '取消',
                type: 'warning',
            }
        )
            .then(() => {
                const idList: string[] = multipleSelection.map((item: any) => item.userId as string);
                deleteUserApi(idList)
                    .then((data: any) => {
                        if ("000000" === data.code) {
                            ElMessage({
                                message: '删除成功',
                                type: 'success',
                            });
                            getUserListHook();
                        }
                    })
                    .catch(error => {
                        console.log(error);
                        ElMessage({
                            message: '删除失败',
                            type: 'error',
                        });
                    })
            })
            .catch(() => {
                ElMessage({
                    type: 'info',
                    message: '已取消',
                })
            })
    }
};

export const deleteOne = function (userId: string) {
    ElMessageBox.confirm(
        `确认删除?`,
        `删除用户`,
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(() => {
            const idList: string[] = [userId];
            deleteUserApi(idList)
                .then((data: any) => {
                    if ("000000" === data.code) {
                        ElMessage({
                            message: '删除成功',
                            type: 'success',
                        });
                        getUserListHook();
                    }
                })
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: '已取消',
            })
        });
};