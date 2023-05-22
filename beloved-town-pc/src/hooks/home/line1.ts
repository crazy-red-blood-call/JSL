// 局部引入echarts核心模块
import * as echarts from 'echarts'

export const line1Hook = function (doms: any) {
    // const pie1 = ref(); // 创建DOM引用
    var myChart = echarts.init(doms.value, 'svg')
    // 绘制图表
    myChart.setOption(option);
};

const option = {
    xAxis: {
        type: 'category',
        data: ['2022-09', '2022-10', '2022-11', '2022-12', '2023-01', '2023-02', '2023-03']
    },
    yAxis: {
        type: 'value'
    },
    grid: {
        left: '0%',
        right: '5%',
        bottom: '10%',
        top: '5%',
        containLabel: true
    },
    series: [
        {
            data: [150, 230, 147, 167, 150, 230, 147, 16],
            type: 'line',
            smooth: true,
            color:'#bfa'
        },
        {
            data: [254, 150, 54, 241, 154, 320, 301, 400],
            type: 'line',
            smooth: true,
            color:'#F4606C'
        }
    ],
    tooltip: {
        trigger: 'item'
    },
};

