// 局部引入echarts核心模块
import * as echarts from 'echarts'

export const lineTraffic = function (doms: any) {
    // const pie1 = ref(); // 创建DOM引用
    let myChart = echarts.init(doms.value)
    // 绘制图表
    myChart.setOption(option);

    window.addEventListener('resize', () => {
        myChart.resize()
    })
};


let result = [
    {name: '09', '访问量': 25, '转化量': 50},
    {name: '10', '访问量': 50, '转化量': 35},
    {name: '11', '访问量': 29, '转化量': 25},
    {name: '12', '访问量': 26, '转化量': 27},
    {name: '13', '访问量': 30, '转化量': 34},
    {name: '14', '访问量': 70, '转化量': 39},
    {name: '15', '访问量': 75, '转化量': 28},
    {name: '16', '访问量': 26, '转化量': 50},
    {name: '17', '访问量': 36, '转化量': 26},
    {name: '18', '访问量': 62, '转化量': 35},
    {name: '19', '访问量': 43, '转化量': 86},
    {name: '20', '访问量': 75, '转化量': 50},
    {name: '21', '访问量': 26, '转化量': 59},
    {name: '22', '访问量': 24, '转化量': 62}
]

let xData = result.map((item) => {
    return item.name
})
let keys = ['访问量', '转化量'],
    seriesData: any = []
let color = ['0, 254, 210', '0, 124, 250']
keys.forEach((key, index) => {
    seriesData.push({
        name: key,
        data: result.map((item) => {
            // @ts-ignore
            return item[key]
        }),
        type: 'line',
        yAxisIndex: 0,
        symbol: 'none',
        symbolSize: 7,
        barWidth: 14,
        splitNumber: 6,
        smooth: false,
        itemStyle: {
            color: `rgba(${color[index]},1)`
        },
        lineStyle: {
            color: `rgba(${color[index]},1)`,
            width: 1
        },
        areaStyle: {
            opacity: 1,
            color: {
                x: 0,
                y: 0,
                x2: 0,
                y2: 1,
                type: 'linear',
                global: false,
                colorStops: [
                    {
                        offset: 0,
                        color: `rgba(${color[index]},0.35)`
                    },
                    {
                        offset: 0.8,
                        color: 'rgba(6, 8, 41,.1)'
                    }
                ]
            },
            shadowColor: 'rgba(0, 0, 0, 0.1)',
            shadowBlur: 10
        }
    })
    seriesData.push({
        name: key + '数',
        data: [
            {
                coords: result.map((item) => {
                    // @ts-ignore
                    return [item.name, item[key]]
                })
            }
        ],
        type: 'lines',
        coordinateSystem: 'cartesian2d',
        zlevel: 1,
        polyline: true,
        smooth: true,
        symbol: 'circle',
        effect: {
            show: true,
            trailLength: 0.4,
            symbol: 'circle',
            period: 8,
            symbolSize: 4
        },
        lineStyle: {
            // normal: {
            //     color: `rgba(${color[index]},1)`,
            //     width: 0,
            //     opacity: 0,
            //     curveness: 0
            // },
            color: `rgba(${color[index]},1)`,
            width: 0,
            opacity: 0,
            curveness: 0
        }
    })
});

const option = {
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'line'
        },
        backgroundColor: 'rgba(12, 51, 115,0.8)',
        borderColor: 'rgba(3, 11, 44, 0.5)',
        textStyle: {
            color: 'rgba(255, 255, 255, 1)'
        }
    },
    grid: {
        x: 30,
        y: 10,
        x2: 20,
        y2: 30
    },
    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: xData,
        axisPointer: {
            handle: {
                show: false
            }
        },
        axisLine: {
            show: false,
            lineStyle: {
                color: '#AEC6DF'
            }
        },
        axisTick: {
            show: true,
            lineStyle: {
                color: '#6f6fa7'
            }
        },
        axisLabel: {
            fontSize: 10,
            interval: 'auto',
            color: '#6f6fa7'
        }
    },
    yAxis: [
        {
            type: 'value',
            splitLine: {
                show: true,
                lineStyle: {
                    color: '#1c1b67'
                }
            },
            axisLabel: {
                fontSize: 10,
                interval: 'auto',
                color: '#6f6fa7'
            },
            axisLine: {
                show: true,
                lineStyle: {
                    color: 'rgba(111,111,167,0.2)'
                }
            }
        }
    ],
    series: seriesData
}

