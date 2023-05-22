import * as echarts from 'echarts'

export const pieOrder = function (doms: any) {
    // const pie1 = ref(); // 创建DOM引用
    var myChart = echarts.init(doms.value)
    // 绘制图表
    myChart.setOption(option);
    activeLoop(myChart);
    // activeLoop(myChart)
    window.addEventListener('resize', () => {
        myChart.resize()
    })
};


var myData = [
    {
        value: 1370,
        name: '鞋类',
    },
    {
        value: 2303,
        name: '数码类',
    },
    {
        value: 1240,
        name: '零食类',
    },
    {
        value: 3180,
        name: '日用品类',
    },
];


let option = {
    color: ['#1aa3ff', '#04f9fa', '#16bd87', '#c961ff', '#7cfc12'],
    legend: {
        bottom: '3%',
        itemWidth: 10,
        itemHeight: 10,
        textStyle: {
            color: '#fff',
            fontSize: '14',
        },
        right: 'right',
        top: 'center'
    },
    series: [
        // 内圈
        {
            type: 'pie',
            center: ['50%', '50%'],
            radius: ['44%', '48%'],
            silent: true, //取消高亮
            label: {show: false, position: 'center'},
            data: myData,
        },
        // 外圈
        {
            type: 'pie',
            center: ['50%', '50%'],
            radius: ['55%', '75%'],
            label: {show: false, position: 'center'},
            emphasis: {
                label: {
                    show: true,
                    fontSize: 16,
                    lineHeight: 20,
                    formatter: (params: any) => {
                        return '{name|' + params.name + '}\n{value|' + params.value + '}';
                    },
                    rich: {
                        name: {
                            color: '#fff',
                        },
                        value: {
                            color: '#04F9FA',
                        },
                    },
                },
            },
            data: myData,
        },
    ],
};

const activeLoop = function (myChart: any) {
    // 循环高亮显示   seriesIndex：1   外圈高亮
    let index = -1; // 高亮下标
    const dataLength = myData.length;
    var timeoutloop = setInterval(() => {
        // 清除之前的高亮
        myChart.dispatchAction({
            type: 'downplay',
            seriesIndex: 1,
            dataIndex: index,
        });
        myChart.dispatchAction({
            type: 'hideTip',
            seriesIndex: 1,
            dataIndex: index,
        });
        index = (index + 1) % dataLength;
        // 当前下标高亮
        myChart.dispatchAction({
            type: 'highlight',
            seriesIndex: 1,
            dataIndex: index,
        });
        myChart.dispatchAction({
            type: 'showTip',
            seriesIndex: 1,
            dataIndex: index,
        });
    }, 2000);
    // 鼠标划入
    myChart.on('mouseover', (e: any) => {
        // 停止定时器，清除之前的高亮
        clearInterval(timeoutloop);
        myChart.dispatchAction({
            type: 'downplay',
            seriesIndex: 1,
            dataIndex: index,
        });
        myChart.dispatchAction({
            type: 'hideTip',
            seriesIndex: 1,
            dataIndex: index,
        });
    });
    // 鼠标离开
    myChart.on('mouseout', (e: any) => {
        activeLoop(myChart);
    });
}