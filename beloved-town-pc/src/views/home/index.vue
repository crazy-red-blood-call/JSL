<template>
    <div class="home-class">
        <div class="link-to">
            <el-button type="success" @click="toBigScreen">前往大屏展示</el-button>
            <el-button type="success" @click="toIpScreen">前往IP展示</el-button>
            <el-button type="danger" @click="longinout">退出</el-button>
        </div>
        <div class="pie">
            <div ref="pie1" style="width:250px;height:250px;"></div>
            <div ref="pie2" style="width:250px;height:250px;"></div>
            <div ref="pie3" style="width:250px;height:250px;"></div>
            <div ref="pie4" style="width:250px;height:250px;"></div>
        </div>
        <div class="bar">
            <div ref="bar1" style="width:45%;height:350px;"></div>
            <div ref="bar2" style="width:45%;height:350px;"></div>
        </div>
        <div class="line">
            <div ref="line1" style="width:100%;height:250px;"></div>
        </div>
    </div>
</template>

<script lang="ts" setup>
import {useRouter} from "vue-router";
import {nextTick, ref} from "vue";
import {pie1Hook} from "@/hooks/home/pie1";
import {pie2Hook} from "@/hooks/home/pie2";
import {pie3Hook} from "@/hooks/home/pie3";
import {pie4Hook} from "@/hooks/home/pie4";
import {bar1Hook} from "@/hooks/home/bar1";
import {bar2Hook} from "@/hooks/home/bar2";
import {line1Hook} from "@/hooks/home/line1";

const pie1 = ref();
const pie2 = ref();
const pie3 = ref();
const pie4 = ref();
const bar1 = ref();
const bar2 = ref();
const line1 = ref();

nextTick(() => {
    pie1Hook(pie1);
    pie2Hook(pie2);
    pie3Hook(pie3);
    pie4Hook(pie4);
    bar1Hook(bar1);
    bar2Hook(bar2);
    line1Hook(line1);
})
const router = useRouter();

const longinout = function () {
    router.push({name: 'login'})
}

const toBigScreen = function () {
    router.push({name: 'bigscreen'})
}
const toIpScreen = function () {
    router.push({name: 'ipscreen'})
}

</script>

<style lang="less" scoped>

.home-class {
  width: 100%;
  height: calc(100vh - 10px);
  //background-color: #01183c;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  justify-content: space-between;


  .pie {
    display: flex;
    justify-content: space-between;
    background-color: #fff;
    padding: 10px 40px 0;
    height: 250px;
    border-radius: 0px 0 10px 10px;
    //box-shadow: 0 0 5px 5px #f1f0f0
  }

  .bar {
    display: flex;
    justify-content: space-between;
    background-color: #fff;
    padding: 10px 40px 0;
    //height: 250px;
    border-radius: 10px;
    //box-shadow: 0 0 5px 5px #e0dfdf;
    margin-top: 15px;
  }

  .line {
    background-color: #fff;
    padding: 10px 40px 0;
    //height: 250px;
    border-radius: 10px;
    //box-shadow: 0 0 5px 5px #e0dfdf;
    margin-top: 15px;
  }

  .link-to {
    background-color: #fff;
    border-bottom: 1px solid #e1e0e0;
    padding: 10px;
    text-align: right;
  }
}

</style>
