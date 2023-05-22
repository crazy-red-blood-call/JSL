<template>
    <div>
        <div class="user-opera-header">
            <el-row class="mb-4">
                <el-button type="success" round size="small" @click="showAdd">新增</el-button>
                <el-button type="danger" round size="small" @click="deleteBatch">删除</el-button>
<!--                <el-button type="primary" round size="small" @click="updateLock(1)">锁定</el-button>-->
<!--                <el-button type="warning" round size="small" @click="updateLock(0)">解锁</el-button>-->
            </el-row>

            <el-form :inline="true" :model="userParam" class="demo-form-inline">
                <el-form-item label="账号">
                    <el-input v-model="userParam.accountName" clearable/>
                </el-form-item>
                <el-form-item label="姓名">
                    <el-input v-model="userParam.userName" clearable/>
                </el-form-item>
                <el-form-item label="状态">
                    <el-select v-model="userParam.lockFlag" placeholder="请选择" clearable>
                        <el-option label="锁定" :value="1"/>
                        <el-option label="正常" :value="0"/>
                    </el-select>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="search">查询</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script lang="ts" setup>

import {userStore} from "@/pinia/user";
import {deleteBatch} from "@/hooks/user/deleteUser";
import {search} from "@/hooks/user/getUserList";
import {updateLock} from "@/hooks/user/updateLock";
import {userEditForm} from "@/hooks/user/userConfig";

const userStores = userStore();
const {userParam} = userStores;

const showAdd = function () {
    userEditForm.form = {};
    userEditForm.tile = '新增用户';
    userEditForm.disabled = [];
    userEditForm.visible = true;
}

</script>

<style scoped lang="less">
.user-opera-header {
  padding-top: 15px;
  display: flex;
  flex-direction: column;
  height: 100px;
  justify-content: space-between;

  :deep(.el-input__wrapper) {
    width: 150px;
  }
}
</style>
