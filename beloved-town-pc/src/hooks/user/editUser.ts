import {ElMessage, FormInstance, FormRules} from "element-plus";
import {addUserApi} from "@/api/api";
import {userStore} from "@/pinia/user";
import {reactive, ref} from "vue";
import {IUserInfo} from "@/types/user/userInfo";
import {getUserListHook} from "@/hooks/user/getUserList";
import {userEditForm} from "@/hooks/user/userConfig";

export let newUser = reactive<IUserInfo>({});

export const resetNewUser = function () {
    newUser = reactive<IUserInfo>({});
};

export const addUser = async (formEl: FormInstance | undefined, newUser: any) => {
    if (!formEl) return;
    await formEl.validate((valid, fields) => {
        if (valid) {
            addUserApi(newUser)
                .then((res: any) => {
                    if ("000000" === res.code) {
                        ElMessage({
                            message: '成功',
                            type: 'success',
                        })
                        getUserListHook();
                        userEditForm.visible = false;
                        console.log('==:??',userEditForm)
                    }
                });
        } else {
            console.log('add error!', fields)
        }
    })
};

export const rules = reactive<FormRules>({
    accountName: [
        {required: true, message: '登录账号不能为空', trigger: 'blur'},
    ],
    userName: [
        {required: true, message: '姓名不能为空', trigger: 'blur'},
    ],
    phone: [
        {required: true, message: '手机号码不能为空', trigger: 'blur'},
    ],
    gender: [
        {required: true, message: '性别不能为空', trigger: 'blur'},
    ],
});

export const ruleFormRef = ref<FormInstance>();