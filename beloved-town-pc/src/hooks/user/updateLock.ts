import {ElMessage, ElMessageBox} from "element-plus";
import {IUpdateLockParam} from "@/types/user/userInfo";
import {updateUserLockApi} from "@/api/api";
import {getUserListHook} from "./getUserList";
import {userStore} from "@/pinia/user";

export const updateLock = function (lockFlag: number) {
    const userStores = userStore();
    let multipleSelection = userStores.getSelectedUserList;
    const lockName = lockFlag === 1 ? '锁定' : '解锁';
    if (multipleSelection.length == 0) {
        ElMessage({
            message: `请勾选你要${lockName}的数据`,
            type: 'warning',
        });
    } else {
        ElMessageBox.confirm(
            `确认${lockName}?`,
            `${lockName}用户`,
            {
                confirmButtonText: '确认',
                cancelButtonText: '取消',
                type: 'warning',
            }
        )
            .then(() => {
                const idList: string[] = multipleSelection.map((item: any) => item.userId as string);
                const updateLockParam: IUpdateLockParam = {
                    userIdList: idList,
                    lockFlag: lockFlag
                };
                updateUserLockApi(updateLockParam)
                    .then((data: any) => {
                        if ("000000" === data.code) {
                            ElMessage({
                                message: `${lockName}成功`,
                                type: 'success',
                            });
                            // 触发查询用户列表
                            getUserListHook();
                        }
                    })
                    .catch(error => {
                        console.log(error);
                        ElMessage({
                            message: `${lockName}失败`,
                            type: 'error',
                        });
                    })
            })
            .catch(() => {
                ElMessage({
                    type: 'info',
                    message: '已取消',
                })
            });
    }
};
