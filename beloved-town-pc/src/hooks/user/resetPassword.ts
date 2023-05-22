import {ElMessage, ElMessageBox} from "element-plus";
import {resetPasswordApi} from "@/api/api";
import {getUserListHook} from "@/hooks/user/getUserList";

export const resetPassword = function (userId: string) {
    ElMessageBox.confirm(
        `确认重置密码?`,
        `重置密码`,
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(() => {
            resetPasswordApi(userId)
                .then((data: any) => {
                    // console.log('--pp>', data);
                    if ("000000" === data.code) {
                        ElMessage({
                            message: `重置成功,新密码为${data.data}`,
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
