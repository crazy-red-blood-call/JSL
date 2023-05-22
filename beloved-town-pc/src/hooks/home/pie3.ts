// 局部引入echarts核心模块
import * as echarts from 'echarts'

export const pie3Hook = function (doms: any) {
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
            name: '库存',
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
                {value: 80, name: '男鞋'},
                {value: 421, name: '纸巾'},
                {value: 91, name: '充电宝'},
                {value: 125, name: '雨伞'},
                {value: 401, name: '眼镜'}
            ]
        }
    ]
};