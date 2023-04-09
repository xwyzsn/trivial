<template>
    <div class="tw-w-full tw-h-full">
        <div class="tw-grid tw-grid-cols-2  tw-mt-4 tw-space-y-4 tw-justify-around tw-items-center">
            <div class="tw-space-x-2">
                <span>订单状态：</span>
                <el-radio-group v-model="radio">
                    <el-radio :label="0">全部</el-radio>
                    <el-radio :label="1">已完成</el-radio>
                    <el-radio :label="2">未完成</el-radio>
                </el-radio-group>
            </div>
            <div class="tw-space-x-2">
                <span>充电站：</span>
                <el-select v-model="stationVal" placeholder="请选择">
                    <el-option v-for="item in stations" :key="item.id" :label="item.name" :value="item.id">
                    </el-option>
                </el-select>
            </div>
            <div class="tw-space-x-4">
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
            <div class="tw-space-x-2">
                    <span>订单号筛选:</span>
                    <el-input style="display: inline;margin-left: 4px;" v-model="orderId" placeholder="请输入订单号"></el-input>
            </div>
            <div>
                <el-button type="primary" @click="search">搜索</el-button>
            </div>
        </div>
        <div v-if="filterData" class="tw-w-full tw-space-x-4 tw-mt-20 tw-h-32 tw-grid tw-grid-cols-4">
            <div class="tw-w-full">
                <el-statistic title="总订单数" class="tw-w-1/3 tw-m-auto tw-mt-10" value-style="color:grey;font-size:44px" :value="filterData.length" />
            </div>
            <div class="tw-w-full">
                <el-statistic title="充电时间" class="tw-w-1/3 tw-m-auto tw-mt-10" value-style="color:orange;font-size:44px" :value="totalMinutes" />
            </div>
            <div class="tw-w-full">
                <el-statistic title="充电度数" class="tw-w-1/3 tw-m-auto tw-mt-10" value-style="color:green;font-size:44px" :value="totalMinutes * 1.5" />
            </div>
            <div class="tw-w-full">
                <el-statistic title="预计收益" class="tw-w-1/3 tw-m-auto tw-mt-10" value-style="color:red;font-size:44px" :value="cost" />
            </div>
        </div>
    </div>
</template>
 
<script setup>
import { ref } from 'vue'
import { api } from '../../boot/api';
import dayjs from 'dayjs';
import { fromPairs } from 'lodash';
let radio = ref(0)
let stationVal = ref(null)
let stations = ref([])
let start = ref('')
let orderId = ref('')
let end = ref('')
let filterData = ref([ ])
let totalMinutes = ref(0)
let cost = ref(0)
api.get('/chargingStation/getall').then(res => {
    stations.value = res.data.data
})
let search = ()=>{
    let formData = {}
    if(orderId.value !== '')formData['orderId']=  orderId.value
    if(stationVal.value !== null)formData['chargerId']= stationVal.value
    if(start.value !== '')formData['startTime'] = dayjs(start.value).format('YYYY-MM-DD hh:mm:ss')
    if(end.value!=='')formData['endTime']= dayjs(end.value).format('YYYY-MM-DD hh:mm:ss')
    formData['type']= radio.value
    // api.get('/orders/searchorder?'+ new URLSearchParams({orderId:orderId.value,chargerId:stationVal.value,startTime:start.value,endTime:end.value
    // ,type:+radio.value}),).then(res=>{
    //     console.log(res.data.data)
    // })
    api({
        url: '/orders/searchorder',
        method: 'get',
        params: formData
    }).then(res => {
        filterData.value = res.data.data
        totalMinutes.value = 0
        cost.value = 0
        filterData.value.forEach(item=>{
            if(item.endTime === null) item.endTime = dayjs().format('YYYY-MM-DD hh:mm:ss')
           let  diff = dayjs(item.endTime).diff(dayjs(item.startTime),'minute')
            totalMinutes.value += diff
            if(item.cost !== null) cost.value += item.cost


        })
    })



}


</script> 
 
<style scoped></style>