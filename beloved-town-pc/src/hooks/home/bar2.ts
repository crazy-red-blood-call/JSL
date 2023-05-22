// 局部引入echarts核心模块
import * as echarts from 'echarts'

export const bar2Hook = function (doms: any) {
    // const pie1 = ref(); // 创建DOM引用
    var myChart = echarts.init(doms.value,'svg')
    // 绘制图表
    myChart.setOption(option);
};


const option = {
    title: {
        text: '销量排行'
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
        data: ['手机', '女鞋',  '眼镜', '衣服']
    },
    series: [
        {
            name: '2022',
            type: 'bar',
            data: [6000, 5500, 5000, 4114, 4744, 3416],
            color:[
                "#0984e3",
            ]
        },
        {
            name: '2023',
            type: 'bar',
            color:[
                "#BEEDC7",
            ],
            data: [7000, 6500, 6000, 5814, 5774, 4716]
        }
    ]
};

