// 局部引入echarts核心模块
import * as echarts from 'echarts'

export const lineSales = function (doms: any) {
    // const pie1 = ref(); // 创建DOM引用
    var myChart = echarts.init(doms.value)
    // 绘制图表
    myChart.setOption(option);

    window.addEventListener('resize', () => {
        myChart.resize()
    })
};


const option = {
    legend: {
        right: '5%',
        top: '-3%',
        textStyle: {
            color: '#5c79de'
        },
        inactiveColor:'rgba(87,87,87,0.62)'
    },
    tooltip: {
        trigger: "axis",
        axisPointer: {
            lineStyle: {
                color: "#86909C", //显示竖线颜色
                type: "dashed",
            },
        },
    },
    dataset: [{}],
    xAxis: {
        type: "category",
        boundaryGap: false,
        axisLine: {
            show: false
        },
        data: [
            "2022-09",
            "2022-10",
            "2022-11",
            "2022-12",
            "2023-02",
            "2023-02",
            "2023-03",
        ],
        axisTick: {
            show: false
        },
        axisLabel: {
            color: "#86909C",
        },
    },
    yAxis: {
        type: "value",
        axisLine: {
            //y轴线
            show: true,
            lineStyle: {
                color: '#1d2046'
            }
        },
        axisTick: {
            //y轴刻度
            show: false,
        },
        axisLabel: {
            // show :false,
            color: "#86909C",
        },

        splitLine: {
            lineStyle: {
                color: "#1d2046",
                width: 1,
                type: "dashed",
            },
        },
    },
    grid: {
        top: "10%",
        right: "6%",
        bottom: "12%",
        left: "3%",
        containLabel: true,
    },
    series: [
        {
            type: "line",
            name: "手机",
            showSymbol: false,
            itemStyle: {
                color: "#165DFF",
                lineStyle: {
                    color: "#165DFF",
                },
            },

            data: [80, 125, 139, 150, 168, 180, 220],
            smooth: true
        },
        {
            type: "line",
            name: "短裙",
            showSymbol: false,
            itemStyle: {
                color: "#1FA087",
                lineStyle: {
                    color: "#1FA087",
                },
            },
            data: [50, 230, 250, 300, 310, 320, 340],
            smooth: true
        },

        {
            type: "line",
            name: "数码",
            showSymbol: false,
            itemStyle: {
                color: "#1FA087",
                lineStyle: {
                    color: "#ac515a",
                },
            },
            data: [30, 200, 230, 240, 270, 290, 340],
            smooth: true
        },
    ],
}
