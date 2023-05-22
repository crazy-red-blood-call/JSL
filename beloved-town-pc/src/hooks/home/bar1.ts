// 局部引入echarts核心模块
import * as echarts from 'echarts'

export const bar1Hook = function (doms: any) {
    // const pie1 = ref(); // 创建DOM引用
    var myChart = echarts.init(doms.value,'svg')
    // 绘制图表
    myChart.setOption(option);
};

const option = {
    title: {
        text: '类目排行'
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'shadow'
        }
    },
    legend: {},
    grid: {
        left: '0%',
        right: '5%',
        bottom: '5%',
        containLabel: true
    },
    xAxis: {
        type: 'value',
        boundaryGap: [0, 0 ]
    },
    yAxis: {
        type: 'category',
        data: ['手机', '女鞋', '纸巾', '眼镜']
    },
    series: [
        {
            name: '2022',
            type: 'bar',
            data: [ 3716, 4744, 4814, 5000, 5500,6000,],
            color:[
                "#F4606C",
            ]
        },
        {
            name: '2023',
            type: 'bar',
            color:[
                "#19CAAD",
            ],
            data: [4716,5744,5814, 6000,  6500, 7000, ]
        }
    ]
};

