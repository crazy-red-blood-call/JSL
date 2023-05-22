// 局部引入echarts核心模块
import * as echarts from 'echarts'

export const barStore = function (doms: any) {
    let myChart = echarts.init(doms.value, 'svg')
    myChart.setOption(option);
    // 绘制图表
    myChart.setOption(option);
    window.addEventListener('resize', () => {
        myChart.resize()
    })
};

let colorArray: string[] = ['#3b7cef', '#fe807e', '#66c2ef', '#feb553', '#9a73ff'];

const option = {
    grid: {
        left: '5%',
        right: '8%',
        bottom: '5%',
        top: '5%',
        containLabel: true
    },
    xAxis: {
        type: 'value',
        axisLabel: false,
        splitLine: {
            show: false
        }
    },
    yAxis: [
        {
            position: 'left',
            type: 'category',
            data: ['女鞋', '电脑', '短裙', '湿巾', '化妆品'],
            axisLine: {
                show: false
            },
            axisTick: false,
            axisLabel: {
                color: '#bbbad9',
                fontWeight: 'blod'
            }
        },
        {
            position: 'right',
            type: 'category',
            data: ['102', '702', '640', '541', '1201'],
            axisLine: {
                show: false
            },
            axisTick: false,
            axisLabel: {
                color: '#bbbad9',
                fontWeight: 'blod'
            }
        }
    ],
    series: [
        {
            name: '2011',
            type: 'bar',
            data: [40, 30, 70, 60, 90],
            itemStyle: {
                borderRadius: 4,
                color: (a: any) => {
                    return colorArray[a.dataIndex]
                }
            },
            barWidth: 8,
            yAxisIndex: 0,
            label: {
                show: true,
                formatter: '{c}%',
                color: '#fff'
            },
        },
        {
            name: '2012',
            type: 'bar',
            data: [100, 100, 100, 100, 100],
            itemStyle: {
                borderColor: '#4b9ed2',
                color: 'none',
                borderRadius: 6,
                borderWidth: 2,
            },
            barWidth: 14,
            yAxisIndex: 1,
        }
    ]
};

