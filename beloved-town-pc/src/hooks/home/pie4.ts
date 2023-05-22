// 局部引入echarts核心模块
import * as echarts from 'echarts'

export const pie4Hook = function (doms: any) {
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
            name: '地区分布',
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
                {value: 1201, name: '北京市'},
                {value: 3241, name: '上海市'},
                {value: 2887, name: '深圳市'},
                {value: 3647, name: '广州市'},
                {value: 1254, name: '武汉市'}
            ]
        }
    ]
};