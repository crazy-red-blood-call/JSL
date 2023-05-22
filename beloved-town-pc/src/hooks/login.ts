import {reactive, toRefs} from "vue";
import {ILoginForm, LoginForm} from '@/types/user/loginForm'
import {loginApi} from '@/api/api'
import md5 from "js-md5";
import {ElMessage} from "element-plus";
import {router} from '@/router'

export const form: { loginForm: ILoginForm, password: string; } = reactive(new LoginForm());

export const toLogin = function () {
    form.loginForm.password = md5(form.password);
    if (!form.loginForm.accountName) {
        ElMessage({
            message: '登录账号不能为空！',
            type: 'error',
        })
        return
    }
    if (!form.password) {
        ElMessage({
            message: '登录密码不能为空！',
            type: 'error',
        });
        return;
    }
    loginApi(form.loginForm)
        .then((data: any) => {
            if (data.data.token) {
                localStorage.setItem("token", data.data.token);
                localStorage.setItem('user', JSON.stringify(data.data));
            }
            ElMessage({
                message: '登录成功',
                type: 'success',
            })
            if (data.data.roleNameList.includes('选品客户')) {
                router.push({
                    name: 'tryit'
                });
            } else if(data.data.roleNameList.includes('设计师')) {
                router.push({
                    name: 'manicure'
                });
            }else{
                router.push({
                    name: 'user'
                });
            }

        })
        .catch((error: any) => {
            console.log('login.error==>', error)
        });
}
