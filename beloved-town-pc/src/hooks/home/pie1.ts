// 局部引入echarts核心模块
import * as echarts from 'echarts'

export const pie1Hook = function (doms: any) {
    // const pie1 = ref(); // 创建DOM引用
    var myChart = echarts.init(doms.value,'svg')
    // 绘制图表
    myChart.setOption(option);
};

const option = {
    tooltip: {
        trigger: 'item'
    },
    // title: {
    //     text: '销量',
    //     subtext: '',
    //     left: 'center'
    // },
    legend: {
        top: '0%',
        left: 'left',
        // orient: 'vertical',
    },
    series: [
        {
            name: '销量',
            type: 'pie',
            radius: ['40%', '60%'],
            avoidLabelOverlap: false,
            label: {
                show: false,
                position: 'center'
            },
            emphasis: {
                label: {
                    show: true,
                    fontSize: 20,
                    fontWeight: 'bold'
                }
            },
            labelLine: {
                show: false
            },
            data: [
                {value: 200, name: '电脑'},
                {value: 735, name: '手机'},
                {value: 580, name: '女鞋'},
                {value: 484, name: '男鞋'},
                {value: 300, name: '衣服'}
            ]
        }
    ]
};
