// 局部引入echarts核心模块
import * as echarts from 'echarts'

export const lineSalesNum = function (doms: any) {
    let myChart = echarts.init(doms.value, 'svg')
    myChart.setOption(option);
    // 绘制图表
    myChart.setOption(option);
    window.addEventListener('resize', () => {
        myChart.resize()
    })
};

const option: any = {
    xAxis: {
        type: 'category',
        data: ['手机', '女鞋', '电脑', '短裙', '湿巾', '化妆品'],
        axisTick: {
            show: false
        },
        axisLine: {
            show: false
        },
        axisLabel: {
            color: '#666383',
            fontWeight: 'bold'
        }
    },
    yAxis: {
        type: 'value',
        axisLine: {
            show: true,
            lineStyle: {
                color: 'rgb(40,40,91)'
            }
        },
        splitLine: {
            lineStyle: {
                color: 'rgb(40,40,91)'
            }
        },
        axisLabel: {
            color: '#666383',
            fontWeight: 'bold'
        }
    },
    series: [
        {
            data: [120, 200, 150, 80, 70, 110, 130],
            type: 'bar',
            itemStyle: {
                borderRadius: [5, 5, 0, 0],
            },
            barWidth: 20,
            color: '#4980d4'
        }
    ],
    grid: {
        left: '5%',
        top: '5%',
        right: '10%',
        bottom: '8%',
        containLabel: true
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'shadow',
            shadowStyle: {
                color: 'rgba(102,99,131,0.3)'
            }
        }
    }
};
