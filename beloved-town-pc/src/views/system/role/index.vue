<template>
    <div class="role-body">
        <div class="role-breadcrumb">
            <el-breadcrumb separator="/" class="breadcrumb">
                <el-breadcrumb-item v-for="item in route.meta.breadcrumb">{{ item }}</el-breadcrumb-item>
                <el-button style="float: right;margin-right: 40px" type="danger"  @click="quit" plain size="small">退出</el-button>
            </el-breadcrumb>
        </div>

        <operaHeader :butList="butList" :formConfig="formConfig"></operaHeader>

        <CommonTable
                :tableInfo="roleTableInfo"
                @selectChange="handleSelectionChange"
                @sizeChange="handleSizeChange"
                @currentChange="handleCurrentChange">
            <template #operation="{row}">
                <el-row class="mb-4">
                    <el-button type="success" plain size="small" @click="showEdit(row)">编辑</el-button>
                    <el-button type="danger" plain size="small" @click="deleteOne(row.id)">删除
                    </el-button>
                </el-row>
            </template>
        </CommonTable>

        <commonEdit :editForm="roleEditForm" @updateEdit="updateEdit" @closeVisible="setVisible">
        </commonEdit>
    </div>
</template>

<script lang="ts" setup>
// import OperaHeader from "@/views/system/role/operaHeader.vue";
import OperaHeader from "@/views/common/operaHeader.vue";
import {useRoute} from "vue-router";
import {onMounted, reactive} from "vue";
import {userStore} from "@/pinia/user";
import {getUserListHook} from "@/hooks/user/getUserList";
import {userEditForm} from "@/hooks/user/userConfig";
import {ElMessage, ElMessageBox} from "element-plus";
import {addRoleUrlApi, addUserApi, deleteRoleApi, deleteUserApi, updateRoleUrlApi} from "@/api/api";
import {addUser} from "@/hooks/user/editUser";
import CommonTable from "@/views/common/commonTable.vue";
import CommonEdit from "@/views/common/commonEdit.vue";
import {getSystemRoleListApi} from "@/api/api";
import {RES_CODE} from "@/api/url";
import hand1 from '@/assets/img/1.jpg'

import {useRouter} from "vue-router";
let router = useRouter();
const quit = function () {
    router.replace({
        name: 'login',
    })
};
const route = useRoute();

let butList = reactive([
    {
        type: 'success',
        round: true,
        size: 'small',
        click() {
            roleEditForm.form = {};
            roleEditForm.tile = '新增角色';
            roleEditForm.action = 'add';
            roleEditForm.disabled = [];
            roleEditForm.visible = true;
        },
        text: "新增",
    }
]);

const statusFormatter = function (row: any) {
    switch (row.status){
        case 1:
            return '正常';
        case 2:
            return '待激活';
        case 0:
            return '已停用';
        default:
            return '';
    }
};
let roleTableInfo = reactive({
    props: [
        {filed: 'code', label: '角色编码', minWidth: 120},
        {filed: 'name', label: '角色名称', minWidth: 120},
        {filed: 'fullName', label: '角色全称', minWidth: 120},
        {filed: 'remark', label: '备注', minWidth: 120},
        {filed: 'status', label: '状态', minWidth: 120, formatter: statusFormatter},
    ],
    operationFlag: true,
    tableList: [],
    selectionFlag: true,
    pageInfo: {
        currentPage: 1,
        pageSize: 10,
        total: 0,
    },
    loading: false
})

let statusOptionList = [
    {
        label: '正常',
        value: 1,
    }, {
        label: '待激活',
        value: 2,
    }, {
        label: '已停用',
        value: 0,
    }
];
let formConfig = reactive({
    param: {},
    fieldList: [{
        label: '角色名称',
        field: 'name',
        type: 'input',
        placeholder: '角色名称',
    },
        {
            label: '状态',
            field: 'status',
            type: 'select',
            placeholder: '选择状态',
            optionList: statusOptionList,
        },
        {
            label: '',
            field: '',
            text: '查询',
            type: 'button',
            backGround: 'primary',
            placeholder: '角色名称',
            click(){
                getData();
            }
        }],

});

let getData = function () {
    roleTableInfo.loading = true;
    let param = {
        ...formConfig.param,
        pageNo: roleTableInfo.pageInfo.currentPage,
        pageSize: roleTableInfo.pageInfo.pageSize,
    };
    getSystemRoleListApi(param as any)
        .then(res => {
            roleTableInfo.tableList = res.data.list;
            roleTableInfo.pageInfo.total = res.data.total;
            roleTableInfo.loading = false;
        }, error => {
            roleTableInfo.loading = false;
        });
};

onMounted(()=>{
    getData();
})


let selected = reactive([]);
let handleSelectionChange = (val: any[]) => {
    selected.splice(0, selected.length);

    val.forEach(item => {
        selected.push(item);
    });
}

let handleSizeChange = (pageSize: number) => {
    roleTableInfo.pageInfo.pageSize = pageSize;
    roleTableInfo.pageInfo.currentPage = 1;
    getData();
};


let handleCurrentChange = (pageNo: number) => {
    roleTableInfo.pageInfo.currentPage = pageNo;
    getData();
}


const showEdit = function (user: any) {
    roleEditForm.form = Object.assign({}, user);
    roleEditForm.tile = '编辑角色';
    roleEditForm.disabled = ['code', 'name'];
    roleEditForm.action = 'update';
    roleEditForm.visible = true;
};


let deleteOne = function (userId: string) {
    ElMessageBox.confirm(
        `确认删除?`,
        `删除角色`,
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(() => {
            const idList: string[] = [userId];
            deleteRoleApi(idList)
                .then((data: any) => {
                    if ("000000" === data.code) {
                        ElMessage({
                            message: '删除成功',
                            type: 'success',
                        });
                        getData();
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

const updateEdit = function (update: any) {
    // addUser(update, userEditForm.form);
    // userEditForm.form = {};

    console.log('--->>>edidg');
    if (!update) return;
    update.validate((valid, fields) => {
        if (valid) {
            // 新增角色
            if (roleEditForm.action === 'add') {
                addRoleUrlApi(roleEditForm.form)
                    .then(res => {
                        let {code, msg} = res;
                        if (code === RES_CODE) {
                            ElMessage({
                                message: '成功',
                                type: 'success',
                            });
                            getData();
                            roleEditForm.visible = false;
                        } else {
                            ElMessage({
                                message: msg,
                                type: 'error',
                            });
                        }
                    });

            } else if (roleEditForm.action === 'update') {
                // 更新角色
                updateRoleUrlApi(roleEditForm.form)
                    .then(res => {
                        let {code, msg} = res;
                        if (code === RES_CODE) {
                            ElMessage({
                                message: '成功',
                                type: 'success',
                            });
                            getData();
                            roleEditForm.visible = false;
                        } else {
                            ElMessage({
                                message: msg,
                                type: 'error',
                            });
                        }
                    });
            }
        } else {
            console.log('add error!', fields)
        }
    });
};


const roleEditForm = reactive({
    visible: false,
    tile: '',
    width: 500,
    form: {},
    action: '',
    disabled: [] as string[],
    props: [
        {label: '编码', prop: 'code', width: 80, filed: 'code'},
        {label: '角色名称', prop: 'name', width: 80, filed: 'name'},
        {label: '角色全称', prop: 'fullName', width: 80, filed: 'fullName'},
        {label: '备注', prop: 'remark', width: 80, filed: 'remark'},
        {
            label: '状态', prop: 'status', width: 80, filed: 'status', type: 'selector', selectOptions: statusOptionList,
        },
    ],
    rules: {
        code: [
            {required: true, message: '编码不能为空', trigger: 'blur'},
        ],
        name: [
            {required: true, message: '角色名称不能为空', trigger: 'blur'},
        ]
    }
})


const setVisible = function (value: boolean) {
    roleEditForm.visible = value;
};
</script>

<style lang="less" scoped>


.role-body {
  background-color: #fff;
  height: 100%;
  padding: 10px;

  .role-breadcrumb {
    border-bottom: 1px solid #D8D8D8;

    .breadcrumb {
      height: 35px;
      width: 100%;
      line-height: 35px;
      color: #B2B3C9;
    }
  }

}

</style>
