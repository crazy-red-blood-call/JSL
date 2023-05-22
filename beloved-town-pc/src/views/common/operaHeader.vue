<template>
    <div>
        <div class="user-opera-header">
            <el-row class="mb-4" v-if="props.butList &&props.butList.length>0">
                <el-button v-for="(item,index) in props.butList" :key="index" :type="item.type" :round="item.round" :size="item.size" @click="item.click">{{item.text}}</el-button>
            </el-row>

            <el-form :inline="true" :model="props.formConfig.param" class="demo-form-inline">
                <el-form-item v-for="(item,index) in props.formConfig.fieldList" :key="index" :label="item.label">

<!--                    输入框-->
                    <el-input v-if="item.type==='input'" v-model="props.formConfig.param[item.field]" clearable/>

<!--                    下拉单选-->
                    <el-select v-else-if="item.type==='select'" v-model="props.formConfig.param[item.field]" :placeholder="item.placeholder" clearable>
                        <el-option v-for="(option,optionIndex) in item.optionList" :key="optionIndex" :label="option.label" :value="option.value"/>
                    </el-select>

<!--                    操作按钮-->
                    <el-button v-else-if="item.type==='button'" :type="item.backGround" @click="item.click">{{item.text}}</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script lang="ts" setup>

import {userStore} from "@/pinia/user";
import {userEditForm} from "@/hooks/user/userConfig";

let props = defineProps(['butList', 'formConfig']);

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
