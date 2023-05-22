<template>
    <div>
        <el-dialog v-model="userStores.addUserVisible" title="新增用户" :width="460">
            <template #header="{ close, titleId, titleClass }">
                <div class="my-header">
                    <h4 :id="titleId" :class="titleClass">新增用户</h4>
                </div>
            </template>
            <el-form :model="newUser" :rules="rules" ref="ruleFormRef">
                <el-form-item label="登录账号" prop="accountName" :label-width="80">
                    <el-input v-model="newUser.accountName"/>
                </el-form-item>
                <el-form-item label="姓名" prop="userName" :label-width="80">
                    <el-input v-model="newUser.userName"/>
                </el-form-item>
                <el-form-item label="手机号码" prop="phone" :label-width="80">
                    <el-input v-model="newUser.phone"/>
                </el-form-item>
                <el-form-item label="性别" prop="gender" :label-width="80">
                    <el-input v-model="newUser.gender"/>
                </el-form-item>
            </el-form>
            <template #footer>
                  <span class="dialog-footer">
                    <el-button @click="userStores.setAddUserVisible(false)">取消</el-button>
                    <el-button type="primary" @click="addUser(ruleFormRef)">
                      确认
                    </el-button>
                  </span>
            </template>
        </el-dialog>
    </div>
</template>

<script lang="ts" setup>
import {userStore} from "@/pinia/user";
import {newUser, addUser, rules, ruleFormRef, resetNewUser} from "@/hooks/user/editUser";
const {user} = defineProps(['user']);

const userStores = userStore();
userStores.$subscribe((mutation: any, state) => {
    if (mutation.events.key === "addUserVisible") {
        resetNewUser();
    }
});


</script>

<style scoped>

</style>