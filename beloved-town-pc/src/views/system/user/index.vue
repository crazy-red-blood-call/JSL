<template>
    <div class="user-body">
        <div class="user">
            <el-breadcrumb separator="/" class="breadcrumb">
                <el-breadcrumb-item v-for="item in route.meta.breadcrumb">{{ item }}</el-breadcrumb-item>
                <el-button style="float: right;margin-right: 40px" type="danger"  @click="quit" plain size="small">退出</el-button>
            </el-breadcrumb>
        </div>

        <operaHeader></operaHeader>

        <CommonTable
                :tableInfo="userTableInfo"
                @selectChange="handleSelectionChange"
                @sizeChange="handleSizeChange"
                @currentChange="handleCurrentChange"
        >
            <template #operation="{row}">
                <el-row class="mb-4">
                    <el-button type="success" plain size="small" @click="showEdit(row)">编辑</el-button>
                    <el-button type="success" plain size="small" @click="authorized(row)">分配角色</el-button>
                    <el-button type="danger" plain size="small" @click="deleteOne(row.userId)">删除
                    </el-button>
                    <el-button type="primary" plain size="small" @click="resetPassword(row.userId)">
                        重置密码
                    </el-button>
                </el-row>
            </template>
        </CommonTable>

        <commonEdit :editForm="userEditForm" @updateEdit="updateEdit" @closeVisible="setVisible">
        </commonEdit>



        <div>
            <el-dialog
                v-model="dialogVisible"
                title="分配角色"
                width="800"
            >
               <div class="transfer-box">
                   <el-transfer
                       v-model="value"
                       filterable
                       :filter-method="filterMethod"
                       filter-placeholder="搜索角色"
                       :data="data"
                       :titles="['未分配角色', '已分配角色']"
                       @change="handleChange"
                   />
               </div>
                <template #footer>
                  <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="authorizedSbumit">
                      确认
                    </el-button>
                  </span>
                </template>
            </el-dialog>


        </div>
    </div>
</template>

<script lang="ts" setup>
import {useRoute} from "vue-router";
import OperaHeader from "./operaHeader.vue";
import {handleSelectionChange} from "@/hooks/user/selectUser";
import {getUserListHook, handleCurrentChange, handleSizeChange, search} from "@/hooks/user/getUserList";
import {deleteOne} from "@/hooks/user/deleteUser";
import {resetPassword} from "@/hooks/user/resetPassword";
import CommonTable from "@/views/common/commonTable.vue";
import CommonEdit from "@/views/common/commonEdit.vue";
import {addUser} from "@/hooks/user/editUser";
import {userEditForm, userTableInfo} from "@/hooks/user/userConfig";
import {onMounted, reactive, ref} from "vue";
import {getSystemRoleListApi, updateRoleUserApi} from "@/api/api";
import {RES_CODE} from "@/api/url";
import {ElMessage} from "element-plus";
import hand1 from '@/assets/img/1.jpg'

import {useRouter} from "vue-router";
let router = useRouter();
const quit = function () {
    router.replace({
        name: 'login',
    })
};
const route = useRoute();
onMounted(() => {
    search();
});
const showEdit = function (user: any) {
    userEditForm.form = Object.assign({}, user);
    userEditForm.tile = '编辑用户';
    userEditForm.disabled = ['accountName'];
    userEditForm.visible = true;
};

const authorized = function (user: any) {
    dialogVisible.value = true;
    userId.value = user.userId;
    value.value.splice(0, value.value.length);
    if (user.roleIdList) {
        user.roleIdList.forEach(item => {
            data.forEach(roles=>{
                if (roles.initial === item) {
                    value.value.push(roles.key);
                }
            })
        });

    } else {
        value.value = [];
    }
};

const authorizedSbumit = function () {
    roleUser.userId = userId.value;
    if (roleUser.roleIdList && roleUser.roleIdList.length > 1) {
        ElMessage({
            message: "只能绑定一个角色",
            type: "error",
        })
        return
    }
    updateRoleUserApi(roleUser)
        .then(res => {
            let {code, msg} = res;
            if (code === RES_CODE) {
                ElMessage({
                    message: '成功',
                    type: 'success',
                });
                getUserListHook();
                dialogVisible.value = false;
            } else {
                ElMessage({
                    message: msg,
                    type: 'error',
                });
            }
        });
};

let value = ref([]);
const filterMethod = (query, item) => {
    if (query) {
        return item.label.includes(query);
    } else {
        return item;
    }
}

interface Option {
    key: number
    label: string
    initial: string
}

const data = reactive<Option[]>([])

onMounted(() => {
    data.splice(0, data.length);
    getSystemRoleListApi({pageNo: 1, pageSize: 100})
        .then(res => {
            res.data.list.forEach((item, index) => {
                data.push({
                    label: item.name,
                    key: index,
                    initial: item.id,
                })
            });
        }, error => {
        });
});

onMounted(() => {
    userTableInfo.loading = true;
})

let dialogVisible = ref(false);

const updateEdit = function (update: any) {
    addUser(update, userEditForm.form);
    // userEditForm.form = {};
};

const setVisible = function (value: boolean) {
    userEditForm.visible = value;
};

let userId = ref('');
let roleUser = reactive({
    userId: '',
    roleIdList: [],
});
const handleChange = (
    value: number | string,
    direction: 'left' | 'right',
    movedKeys: string[] | number[]
) => {
    roleUser.roleIdList.splice(0, roleUser.roleIdList.length);
    value.forEach(item=>{
        roleUser.roleIdList.push(data[item].initial);
    })
}
</script>

<style lang="less" scoped>
.user-body {
  background-color: #fff;
  height: 100%;
  padding: 10px;

  .user {
    border-bottom: 1px solid #D8D8D8;

    .breadcrumb {
      height: 35px;
      width: 100%;
      line-height: 35px;
      color: #B2B3C9;
    }
  }

    .transfer-box{
        text-align: center;
    }
}

</style>
