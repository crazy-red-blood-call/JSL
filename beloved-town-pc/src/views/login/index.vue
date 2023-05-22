<script setup lang="ts">
import {toLogin, form} from '@/hooks/login'
</script>

<template>
    <div class="login-bg">
        <div class="login-form" @keyup.enter="toLogin">
            <h1>美甲试戴系统</h1>
            <el-input v-model="form.loginForm.accountName" placeholder="请输入账号" clearable/>
            <el-input v-model="form.password" placeholder="请输入密码" clearable type="password"
                      show-password/>
            <div class="login-button">
                <el-button type="primary" @click="toLogin">登录</el-button>
<!--                <el-button type="success">注册</el-button>-->
            </div>
        </div>
    </div>
</template>

<style scoped lang="less">
.login-bg {
  height: 100vh;
  background-image: url("@/assets/img/bg.png");
  background-repeat: no-repeat;
  background-size: 100% 100%;

  .login-form {
    width: 480px;
    height: 280px;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translateX(-50%) translateY(-50%);
    background-color: rgba(255, 255, 255, 0.05);
    box-shadow: 0px 3px 3px rgba(255, 255, 255, 0.7);
    border: 1px solid rgba(255, 255, 255, 0.5);
    border-radius: 10px;
    padding: 20px 50px 0;

    &:hover {
        box-shadow: 0px 5px 5px rgba(255, 255, 255, 0.7);
        transform: translateX(calc(-50% + 1px)) translateY(calc(-50% - 1px));
    }


    & > div {
      margin-bottom: 20px;
    }

    & > h1 {
      color: #fff;
      margin-bottom: 42px;
      text-align: center;
    }

    .login-button {
      display: flex;
      justify-content: flex-end;
      margin-top: 30px;
    }

  }
}

.el-alert {
  margin: 20px 0 0;
}

.el-alert:first-child {
  margin: 0;
}
</style>
