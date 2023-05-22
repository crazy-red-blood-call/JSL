// 局部引入echarts核心模块
import * as echarts from 'echarts'


const datas: { name: string, value: number }[] = [
    {
        name: "数码",
        value: 10214,
    },
    {
        name: "衣服",
        value: 21021,
    },
    {
        name: "化妆品",
        value: 30124,
    },
    {
        name: "日用品",
        value: 25410,
    },
];

const option = {
    color: ["#F56463", "#00C6FF", "#F09615", "#0079E6"],
    legend: {
        itemHeight: 8,
        itemWidth: 8,
        icon: "rect",
        orient: "vertical",
        top: "center",
        right: "3%",
        textStyle: {
            align: "left",
            color: "#",
            verticalAlign: "middle",
            rich: {
                name: {
                    width: 35,
                    fontSize: 12,
                },
                value: {
                    width: 50,
                    align: "right",
                    fontFamily: "Medium",
                    fontSize: 12,
                },
                rate: {
                    width: 5,
                    align: "right",
                    fontSize: 12,
                },
            },
        },
        data: datas,
        formatter: (name: string) => {
            if (datas.length) {
                const item = datas.filter((item) => item.name === name)[0];
                return `{name|${name}}{value| ${item.value}} {rate| 件}`;
            }
        },
    },
    tooltip: {
        trigger: "item",
        formatter: "{d}%",
    },
    series: [
        {
            name: "",
            type: "pie",
            radius: ["30%", "80%"],
            center: ["35%", "50%"],
            roseType: "radius",
            label: {
                show: true,
                formatter: "{d}%",
                color: '#fff'
            },
            labelLine: {
                length: 7,
                length2: 5,
            },
            data: datas,
        },
    ],
};
export const pieType = function (doms: any) {
    // const pie1 = ref(); // 创建DOM引用
    var myChart = echarts.init(doms.value)
    // 绘制图表
    myChart.setOption(option);

    window.addEventListener('resize', () => {
        myChart.resize()
    })
};

