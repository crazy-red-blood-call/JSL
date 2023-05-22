<template>
    <div class="screen">

        <div class="top">
            <div class="toHome">
                <el-button type="text" :icon="HomeFilled" size="large" @click="toHome"></el-button>
            </div>
            <div class="title">
                <h1>美甲试戴系统大屏监控</h1>
            </div>
            <div class="time">
                <p>{{ clock.time }}</p>
                <p>{{ clock.date }}</p>
            </div>
        </div>

        <div class="body">
            <div>
                <div class="panel">
                    <h5>销售量</h5>
                    <div class="char" ref="storeNum">
                    </div>
                    <div class="footer"></div>
                </div>
                <div class="panel middle">
                    <h5>销售额</h5>
                    <div class="char" ref="sales"></div>
                    <div class="footer"></div>
                </div>
                <div class="panel">
                    <h5>商品分类分布</h5>
                    <div class="char" ref="type"></div>
                    <div class="footer"></div>
                </div>
            </div>
            <div class="middle-map">
                <div class="showNum">
                    <div class="total">
                        <h3>{{ total }}</h3>
                        <span>成交额</span>
                    </div>
                    <div>
                        <h3>{{ orderNum }}</h3>
                        <span>订单数</span>
                    </div>
                </div>
                <div>
                    <div class="map" ref="map">1</div>
                </div>
            </div>
            <div>
                <div class="panel">
                    <h5>库存量</h5>
                    <div class="char" ref="store"></div>
                    <div class="footer"></div>
                </div>
                <div class="panel middle">
                    <h5>访问流量</h5>
                    <div class="char" ref="traffic"></div>
                    <div class="footer"></div>
                </div>
                <div class="panel">
                    <h5>订单分布</h5>
                    <div class="char" ref="order"></div>
                    <div class="footer"></div>
                </div>
            </div>
        </div>

    </div>
</template>

<script setup lang="ts">
import {nextTick, reactive, ref} from "vue";
import {lineSalesNum} from "@/hooks/screen/line-salesNum";
import {barStore} from "@/hooks/screen/bar-store";
import {lineSales} from "@/hooks/screen/line-sales";
import {lineTraffic} from "@/hooks/screen/line-traffic";
import {pieType} from "@/hooks/screen/pie-type";
import {pieOrder} from "@/hooks/screen/pie-order";
import {chineseMap} from "@/hooks/screen/map";
import {HomeFilled} from "@element-plus/icons-vue";
import {useRouter} from "vue-router";

const clock = reactive({
    date: '',
    time: ''
})

let week = ['星期天', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'];
setInterval(updateTime, 1000);
updateTime();

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


const storeNum = ref();
const store = ref();
const sales = ref();
const traffic = ref();
const type = ref();
const order = ref();
const map = ref();

nextTick(() => {
    // const pie1 = ref(); // 创建DOM引用
    lineSalesNum(storeNum);
    barStore(store);
    lineSales(sales)
    lineTraffic(traffic)
    pieType(type);
    pieOrder(order);
    chineseMap(map);
})

let total = ref(425071);
let orderNum = ref(125811);

setInterval(() => {
    total.value = total.value + 3;
    orderNum.value = orderNum.value + 3;
}, 200)

const router = useRouter();
const toHome = function () {
    router.push({name: 'home'})
}


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

    .panel {
      position: relative;
      height: calc((100vh - 100px) / 3);
      border: 1px solid rgba(157, 141, 187, 0.2);

      h5 {
        height: 25px;
        color: #c3c1d7;
        text-align: center;
        line-height: 25px;
      }

      .char {
        width: 100%;
        height: calc(100% - 25px);
      }

      &::before {
        content: '';
        position: absolute;
        left: 0;
        top: 0;
        width: 5px;
        height: 5px;
        border-top: 1px solid #2984a9;
        border-left: 1px solid #2984a9;
      }

      &::after {
        content: '';
        position: absolute;
        right: 0;
        top: 0;
        width: 5px;
        height: 5px;
        border-top: 1px solid #2984a9;
        border-right: 1px solid #2984a9;
      }

      .footer {
        position: absolute;
        bottom: 0;
        left: 0;
        width: 100%;

        &::before {
          content: '';
          position: absolute;
          left: 0;
          bottom: 0;
          width: 5px;
          height: 5px;
          border-left: 1px solid #2984a9;
          border-bottom: 1px solid #2984a9;
        }

        &::after {
          content: '';
          position: absolute;
          right: 0;
          bottom: 0;
          width: 5px;
          height: 5px;
          border-right: 1px solid #2984a9;
          border-bottom: 1px solid #2984a9;
        }
      }
    }

    .middle {
      margin: 10px 0px;
    }

    .middle-map {

      .showNum {
        height: 70px;
        display: flex;
        justify-content: space-around;
        background-color: #191879;
        margin: 0 10px;

        & > div {
          display: flex;
          flex-direction: column;
          justify-content: space-around;

          & > h3 {
            font-family: 'ds-digit';
            font-size: 35px;
            color: #ffe68b;
            width: 110px;
          }

          & > span {
            color: #a595d4;
            font-size: 14px;
            text-align: center;
            font-weight: bold;
          }
        }
      }

      .map {
        //padding-top: 100px;
        margin-top: 80px;
        //border: 1px solid red;
        height: calc(100vh - 240px)
      }
    }
  }
}

</style>
