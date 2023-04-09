<template>
 <div class="tw-w-full tw-h-full">
    <div class="tw-flex tw-space-x-4 ">
        <div>
            <span>日期筛选</span>
                    <el-date-picker
                        v-model="start"
                        type="date"
                        placeholder="选择开始日期"
                        size="default"
                    />
                    <el-date-picker
                        v-model="end"
                        type="date"
                        placeholder="选择结束日期"
                        size="default"
                    >
                    </el-date-picker>
        </div>
        <div class="tw-ml-4">
            <el-button type="primary" @click="search">查询</el-button>
        </div>
    </div>
    <div class="tw-w-full tw-mt-10 tw-h-2/3">
        <div id="line" style="width: 100%;height:100%;">
        </div>
    </div>
 </div> 
 </template>
 
 <script setup>
 import {ref,onMounted} from 'vue' 
    import { api } from '../../boot/api';
    import dayjs from 'dayjs';
    import * as echarts from 'echarts'
    let data = ref([])
    const start = ref('')
    const end = ref('')
    let option = {
            title: {
                text: '订单统计'
            },
            tooltip: {},
            legend: {
                data:['订单数','收入']
            },
            xAxis: {
                data: []
            },
            yAxis: {},
            series: [{
                name: '订单数',
                type: 'bar',
                data: []
            },{
                name: '收入',
                type: 'line',
                data: []
            }]
        };
    const search = () => {
        let s = dayjs(start.value).format('YYYY-MM-DD hh:mm:ss')
        let e = dayjs(end.value).format('YYYY-MM-DD hh:mm:ss')
        api({
            url:'/orders/searchorder',
            method:'get',
            params:{
                startTime:s,
                endTime:e,
                type:0
            }
        }).then(res=>{
            data.value = res.data.data
            let tmp = {}
            let earn = {}
            data.value.forEach(item=>{
                let day = dayjs(item.startTime).format('YYYY-MM-DD')
                if(tmp[day] === undefined || tmp[day] === null){
                    tmp[day] = 1
                }else{
                    tmp[day]++
                }
                if(earn[day] === undefined || earn[day] === null){
                    earn[day] = item.cost?item.cost:0
                }else{
                    earn[day]+=item.cost?item.cost:0
                }
            })
            let x = Object.keys(tmp)
            let y = Object.values(tmp)
            let myChart = echarts.init(document.getElementById('line'));
            option.xAxis.data = x
            option.series[0].data = y
            option.series[1].data = Object.values(earn)
            myChart.setOption(option);

        })
    }
    onMounted(()=>{

    })
</script> 
 
 <style scoped>
 
</style>