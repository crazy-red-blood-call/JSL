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
                    <div v-if="!isTryFlag">
                        <el-button type="success" plain size="small" @click="showEdit(row)">编辑</el-button>
                        <el-button type="danger" plain size="small" @click="deleteOne(row.id)">删除</el-button>
                    </div>
                    <div v-else>
                        <el-button type="success" plain size="small" @click="tryit(row)">试戴</el-button>
                    </div>

                </el-row>
            </template>
        </CommonTable>

        <commonEdit :editForm="roleEditForm" @updateEdit="updateEdit" @closeVisible="setVisible">
        </commonEdit>

        <div>
            <el-dialog
                v-model="dialogVisible"
                title="美甲试戴"
                width="700"
            >
                <div style="display: flex;justify-content: center">
                    <div style="width: 600px;height: 600px;box-sizing: border-box;position: relative">
                        <el-image style="width: 600px; height: 600px" :src="hand" fit="fill" />

                        <div class="one-box">
                            <el-image style="width: 100%; height: 100%" :src="imgSrc" fit="fill" />
                        </div>
                        <div class="two-box">
                            <el-image style="width: 100%; height: 100%" :src="imgSrc" fit="fill" />
                        </div>
                        <div class="three-box">
                            <el-image style="width: 100%; height: 100%" :src="imgSrc" fit="fill" />
                        </div>
                        <div class="four-box">
                            <el-image style="width: 100%; height: 100%" :src="imgSrc" fit="fill" />
                        </div>
                       <div class="five-style">
                           <div class="five-box">
                               <el-image style="width: 100%; height: 100%" :src="imgSrc" fit="fill" />
                           </div>
                       </div>
                    </div>
                </div>
                <template #footer>
                  <span class="dialog-footer">
                    <el-button type="primary" @click="dialogVisible = false">
                      关闭
                    </el-button>
                  </span>
                </template>
            </el-dialog>
        </div>

    </div>
</template>

<script lang="ts" setup>
// import OperaHeader from "@/views/system/role/operaHeader.vue";
import OperaHeader from "@/views/common/operaHeader.vue";
import {useRoute} from "vue-router";
import {onMounted, onUpdated, reactive, ref} from "vue";
import {userStore} from "@/pinia/user";
import {getUserListHook} from "@/hooks/user/getUserList";
import {userEditForm} from "@/hooks/user/userConfig";
import {ElMessage, ElMessageBox} from "element-plus";
import {
    addManicureApi,
    addUserApi, deleteManicureApi,
    deleteRoleApi,
    deleteUserApi,
    getManicureListApi, updateManicureApi,
    updateRoleUrlApi
} from "@/api/api";
import {addUser} from "@/hooks/user/editUser";
import CommonTable from "@/views/common/commonTable.vue";
import CommonEdit from "@/views/common/commonEdit.vue";
import {getSystemRoleListApi} from "@/api/api";
import {RES_CODE, UPLOAD_URL} from "@/api/url";
import * as url from '@/api/url'
import hand from '@/assets/img/hand.jpg'
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
            roleEditForm.tile = '新增美甲';
            roleEditForm.action = 'add';
            roleEditForm.disabled = [];
            roleEditForm.visible = true;
            roleEditForm.props[3].fileList.splice(0, roleEditForm.props[3].fileList.length);

        },
        text: "新增",
    }
]);

let roleTableInfo = reactive({
    props: [
        {filed: 'title', label: '标题', minWidth: 120},
        {filed: 'content', label: '描述', minWidth: 120},
        {filed: 'status', label: '状态', minWidth: 120, },
        {filed: 'imgs', type: 'img', label: '图片', minWidth: 120, },
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
        label: '启用',
        value: '启用',
    }, {
        label: '停用',
        value: '停用',
    }
];
let formConfig = reactive({
    param: {},
    fieldList: [{
        label: '标题',
        field: 'title',
        type: 'input',
        placeholder: '标题',
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
            placeholder: '美甲名称',
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

    if (isTryFlag.value) {
        param.isKh = '1';
    }else {
        delete param.isKh;
    }
    getManicureListApi(param as any)
        .then(res => {
            roleTableInfo.tableList = res.data.list;
            roleTableInfo.pageInfo.total = res.data.total;
            roleTableInfo.loading = false;
        }, error => {
            roleTableInfo.loading = false;
        });
};

let isTryFlag = ref(false);
onMounted(()=>{
    isTryFlag.value = route.meta.isTryFlag;
    butList.splice(0, butList.length);

    if (!isTryFlag.value) {
        butList.push({
            type: 'success',
            round: true,
            size: 'small',
            click() {
                roleEditForm.form = {};
                roleEditForm.tile = '新增美甲';
                roleEditForm.action = 'add';
                roleEditForm.disabled = [];
                roleEditForm.visible = true;
                roleEditForm.props[3].fileList.splice(0, roleEditForm.props[3].fileList.length);

            },
            text: "新增",
        })
    }
    getData();
})

onUpdated(()=>{
    isTryFlag.value = route.meta.isTryFlag;
    butList.splice(0, butList.length);

    if (!isTryFlag.value) {
        butList.push({
            type: 'success',
            round: true,
            size: 'small',
            click() {
                roleEditForm.form = {};
                roleEditForm.tile = '新增美甲';
                roleEditForm.action = 'add';
                roleEditForm.disabled = [];
                roleEditForm.visible = true;
                roleEditForm.props[3].fileList.splice(0, roleEditForm.props[3].fileList.length);

            },
            text: "新增",
        })
    }
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

let imgSrc = ref('');

const showEdit = function (user: any) {
    roleEditForm.form = Object.assign({}, user);
    roleEditForm.tile = '编辑美甲';
    roleEditForm.disabled = ['title'];
    roleEditForm.action = 'update';
    roleEditForm.visible = true;
    roleEditForm.props[3].fileList.splice(0, roleEditForm.props[3].fileList.length);
    roleEditForm.props[3].fileList.push(
        {
            name: roleEditForm.form.id,
            url: roleEditForm.form.imgs
        }
    )
};

let dialogVisible = ref(false);
const tryit = function (item) {
    dialogVisible.value = true;
    imgSrc.value = item.imgs;

    console.log('--->>', imgSrc);
};

let deleteOne = function (userId: string) {
    ElMessageBox.confirm(
        `确认删除?`,
        `删除美甲`,
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(() => {
            const idList: string[] = [userId];
            deleteManicureApi(idList)
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
            // 新增美甲
            if (roleEditForm.action === 'add') {
                addManicureApi(roleEditForm.form)
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
                // 更新美甲
                updateManicureApi(roleEditForm.form)
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
        {label: '标题', prop: 'title', width: 80, filed: 'title'},
        {label: '描述', prop: 'content', width: 80, filed: 'content'},
        {
            label: '状态',
            prop: 'status',
            width: 80,
            filed: 'status',
            type: 'selector',
            selectOptions: statusOptionList,
        },
        {
            label: '图片', type: 'picture', fileList: [],
            action: url.baseURL + url.UPLOAD_URL, prop: 'imgs', limit: 1, width: 120, filed: 'imgs',
            handlePictureCardPreview() {
            },
            handleRemove() {
            },
            success(response, file, fileList, item) {
                console.log('------->', response, file, fileList, item);
                roleEditForm.form[item.filed] = response.data;
            },
        },

    ],
    rules: {
        code: [
            {required: true, message: '编码不能为空', trigger: 'blur'},
        ],
        name: [
            {required: true, message: '美甲名称不能为空', trigger: 'blur'},
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

.one-box {
    height: 25px;
    width: 18px;
    position: absolute;
    left: 161px;
    top: 170px;
    border-radius: 10px 9px 8px 9px/10px 9px 9px 8px;
    transform: rotateZ(-19deg);
    overflow: hidden;
}
.two-box {
    height: 24px;
    width: 21px;
    position: absolute;
    left: 225px;
    top: 109px;
    border-radius: 19px 13px 14px 18px/21px 16px 27px 10px;
    transform: rotateZ(-10deg);
    overflow: hidden;
}

.three-box {
  height: 24px;
  width: 21px;
  position: absolute;
  left: 310px;
  top: 80px;
  border-radius: 10px 10px 7px 6px/13px 9px 8px 10px;
  overflow: hidden;
}
.four-box {
    height: 18px;
    width: 21px;
    position: absolute;
    left: 392px;
    top: 117px;
    border-radius: 10px 10px 7px 6px/13px 9px 8px 10px;
    overflow: hidden;
    transform: rotateZ(11deg);
}

.five-style{
    height: 20px;
    width: 9px;
    position: absolute;
    left: 438px;
    top: 261px;
    border-radius: 1px 3px 4px 1px/1px 5px 16px 2px;
    overflow: hidden;
    transform: rotateZ(6deg);
    .five-box {
        height: 20px;
        width: 9px;
        position: absolute;
        left: 0;
        top: 0;
        border-radius: 10px 2px 5px 8px/18px 9px 4px 15px;
        overflow: hidden;
        transform: rotateZ(4deg);
    }
}

</style>
