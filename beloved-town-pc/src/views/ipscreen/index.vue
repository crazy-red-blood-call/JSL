<template>
    <div class="screen">

        <div class="top">
            <div class="toHome">
<!--                <el-button type="text" :icon="HomeFilled" size="large" @click="toHome"></el-button>-->
            </div>
            <div class="title">
                <h1>ip监控</h1>
            </div>
            <div class="time">
                <p>{{ clock.time }}</p>
                <p>{{ clock.date }}</p>
            </div>
        </div>

        <div class="body">
            <div class="middle-map">
                <div>
                    <div class="map" ref="map"></div>
                </div>
            </div>
        </div>

    </div>
</template>

<script setup lang="ts">
import {nextTick, reactive, ref} from "vue";
import {useRouter} from "vue-router";
import {chineseMap} from "@/hooks/ipscreen";
// import {ipApi} from "@/api/api";
import {watch} from 'vue'

const clock = reactive({
    date: '',
    time: ''
})

let week = ['星期天', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'];
setInterval(updateTime, 1000);
updateTime();

let data = reactive({
    list: [],
});

watch(data, () => {
    console.log('-->>watch')
    if (data.list && data.list.length > 0) {
        data.list.sort((a, b) => a.value - b.value);
        data.max = data.list[data.list.length - 1].value;
    }
    chineseMap(map, data);
});

function updateTime() {
    const cd = new Date();
    clock.time = zeroPadding(cd.getHours(), 2) + ':' + zeroPadding(cd.getMinutes(), 2) + ':' + zeroPadding(cd.getSeconds(), 2);
    clock.date = zeroPadding(cd.getFullYear(), 4) + '-' + zeroPadding(cd.getMonth() + 1, 2) + '-' + zeroPadding(cd.getDate(), 2) + ' ' + week[cd.getDay()];
}

function zeroPadding(num: number, digit: number) {
    let zero = '';
    for (let i = 0; i < digit; i++) {
        zero += '0';
    }
    return (zero + num).slice(-digit);
}


const map = ref();
const changes = function () {
    data.list = value4;
};
nextTick(() => {
    let param = reactive({});
    // 请求接口并获取数据
    // ipApi(param)
    //     .then(res => {
    //         data.list = res.data;
    //         if (!data.list || data.list.length == 0) {
    //             data.list = value3;
    //         }
    //         data.list = res.data;
    //     }, error => {
    //         console.log('请求接口失败-->', error);
    //         data.list = value3;
    //     });
    data.list = value3;
})

// 示例数据
let value3 =[
    {
        name: '中国',
        value: 954
    },
    {
        name: '俄罗斯',
        value: 2337
    },
    {
        name: '美国',
        value: 6454
    },
]
let value4 =[
    {
        name: '中国',
        value: 11011
    },
    {
        name: '俄罗斯',
        value: 2337
    },
    {
        name: '美国',
        value: 6454
    },
]


let total = ref(425071);
let orderNum = ref(125811);

setInterval(() => {
    total.value = total.value + 3;
    orderNum.value = orderNum.value + 3;
}, 200)


</script>

<style lang="less" scoped>

.screen {

  height: 100vh;
  background-image: radial-gradient(#210ec0, #010235);
  min-width: 800px;
  color: #fff;

  .top {
    box-shadow: 0 0 1px 1px #232449;
    width: 100%;

    .toHome {
      float: left;
      margin-top: 10px;
      //margin-left: 20px;
      color: #409eff;

      & :deep(.el-icon) {
        height: 1.5em;
        width: 1.5em;

        svg {
          height: 1.5em;
          width: 1.5em;
        }
      }
    }

    .title {
      height: 55px;
      width: 300px;
      margin: 0 auto;
      text-align: center;
      line-height: 55px;
      font-size: 14px;
    }

    .time {
      position: absolute;
      right: 0;
      top: 17px;
      display: flex;
      width: 300px;
      margin-right: 40px;
      justify-content: space-between;
      color: #9d8dbb;
      font-weight: bold;
      text-shadow: 0px 0px 4px #0a4d63;

      &::before {
        content: '当前时间:';
      }
    }
  }

  .body {
    display: flex;
    padding: 10px;

    & > div {
      //border: 1px solid red;
      flex: 3;
    }

    & > div:nth-child(2) {
      flex: 5;
    }


    //.middle {
    //  margin: 10px 0px;
    //}

    .middle-map {


      .map {
        //padding-top: 100px;
        //margin-top: 80px;
        //border: 1px solid red;
        height: calc(100vh - 75px)
      }
    }
  }
}

</style>
