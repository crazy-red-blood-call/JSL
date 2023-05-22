// 局部引入echarts核心模块
import * as echarts from 'echarts'

export const pie2Hook = function (doms: any) {
    // const pie1 = ref(); // 创建DOM引用
    var myChart = echarts.init(doms.value)
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
            name: '浏览量',
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
                {value: 2000, name: 'H5'},
                {value: 735, name: '微信小程序'},
                {value: 580, name: 'PC'},
                // {value: 484, name: '男鞋'},
                // {value: 300, name: '衣服'}
            ]
        }
    ]
};