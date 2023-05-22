<template>
    <div class="navigation">
        <div class="left-menu">
            <el-menu
                    default-active="2"
                    class="el-menu-vertical-demo"
                    :collapse="isCollapse"
                    @open="handleOpen"
                    @close="handleClose"
                    active-text-color="#ffd04b"
                    background-color="#545c64"
                    text-color="#fff"
                    router>
                <div v-for="(item,index) in menuList" :key="index">
                    <el-sub-menu :index="item.index"  v-if="item.show && item.childrenList">
                        <template #title >
                            <el-icon><location/></el-icon>
                            <span>{{item.name}}</span>
                        </template>
                        <div v-for="(children,index) in item.childrenList" :key="index">
                            <el-menu-item :index="children.index" v-if="children.show">{{children.name}}</el-menu-item>
                        </div>
                    </el-sub-menu>

                    <el-menu-item :index="item.index" v-else-if="item.show">
                        <el-icon>
                            <icon-menu/>
                        </el-icon>
                        <template #title>{{item.name}}</template>
                    </el-menu-item>

                </div>

            </el-menu>
            <div class="collapse" @click="changCollapse">
                <el-icon size="30" color="#bfa" v-if="isCollapse">
                    <Expand/>
                </el-icon>
                <el-icon size="30" color="#bfa" v-else>
                    <Fold/>
                </el-icon>
            </div>
        </div>
        <div class="right-body">
            <router-view/>
        </div>
    </div>
</template>


<script lang="ts" setup>
import {onMounted, reactive, ref} from 'vue'
import {
    Document,
    Menu as IconMenu,
    Location,
    Setting, Expand, Fold,
} from '@element-plus/icons-vue'

const isCollapse = ref(false)
const changCollapse = function () {
    isCollapse.value = !isCollapse.value;
};

const handleOpen = (key: string, keyPath: string[]) => {
    console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
    console.log(key, keyPath)
}

let menuList = reactive([
    {
        name: '用户管理',
        index: '/navigation/user',
        show: false,
        role: ['超级管理员'],
    },  {
        name: '角色管理',
        index: '/navigation/role',
        show: false,
        role: ['超级管理员'],
    },
    {
        name: '美甲管理',
        index: '/navigation/manicure',
        show: false,
        role: ['设计师','超级管理员'],
    },
    {
        name: '试戴效果',
        index: '/navigation/tryit',
        show: false,
        role: ['选品客户'],
    }
]);

onMounted(()=>{
    let user = JSON.parse(localStorage.getItem('user') as string);
    console.log('---->', user);
    setShow(menuList, user);
})

let setShow = function (menuList: [], user: any) {
    menuList.forEach((item: any) => {
        let roles: [] = item.role;
        roles.forEach(role => {
            if (user.roleNameList.includes(role)) {
                item.show = true;
                return;
            }
        });
        if (item.childrenList) {
            setShow(item.childrenList, user);
        }
    });
};


</script>

<style lang="less">
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
  //height: 100vh;
}

.navigation {
  display: flex;

  .left-menu {
    background-color: #545c64;
    height: 100vh;
    display: flex;
    flex-direction: column;
    justify-content: space-between;

    .collapse {
      float: right;
      text-align: center;
    }
  }

  .right-body {
    flex: 1;
    background-color: #f2f2f2;
    padding: 10px 10px 0px;
    min-width: 800px;
  }
}

</style>
