<template>
    <div>
        <div>
            <el-collapse v-model="currentOrder">
                <el-collapse-item>
                    <template #title>
                        <span>当前订单</span>
                    </template>
                    <el-table v-if="currentOrder" :data="currentOrder.slice(0,currentOrder.length-1)">
                        <el-table-column prop="chargerName" label="充电站名称"> </el-table-column>
                        <el-table-column prop="startTime" label="充电开始时间"></el-table-column>
                        <el-table-column prop="predictTime" label="预计结束"></el-table-column>
                        <el-table-column label="结束充电">
                            <template #default="scope">
                                <el-button type="primary" @click="endCharge(scope.row)">结束充电</el-button>
                            </template>
                        </el-table-column>

                    </el-table>
                </el-collapse-item>
            </el-collapse>

        </div>
        <div>
            <el-collapse v-model="historyVisible">
                <el-collapse-item>
                    <template #title>
                        <span>历史订单</span>
                    </template>
                    <el-table v-if="historyOrder" :data="historyOrder">
                        <el-table-column prop="chargerName" label="充电站名称"></el-table-column>
                        <el-table-column prop="startTime" label="开始时间"></el-table-column>
                        <el-table-column prop="endTime" label="结束时间"></el-table-column>
                        <el-table-column prop="cost" label="花费"></el-table-column>
                    </el-table>
                </el-collapse-item>

            </el-collapse>
        </div>

    </div>
</template>
 
<script setup>
import MyTitle from '../../components/MyTitle.vue';
import { api } from '../../boot/api';
import { ref } from 'vue';
import dayjs from 'dayjs';

let info = ref(JSON.parse(sessionStorage.getItem('info')))
let order = ref([])
let currentOrder = ref([])
let historyOrder = ref([])
let historyVisible = ref(false)
let currentVisible = ref(false)
let vbatterySize = ref(0)
api({ url: '/vehicle/getcar?user_id=' + info.value.userId, method: 'GET' }).then(res => {
    let response = res.data
    if (response.code === 200) {
        vbatterySize.value = response.data[0].batterySize
    }
    console.log(vbatterySize.value)
})
let stations = ref(JSON.parse(sessionStorage.getItem('station')))
api({
    url: '/orders/getorder?user_id=' + info.value.userId,
    method: 'GET'
}).then(res => {
    let response = res.data
    if (response.code === 200) {
        order.value = response.data
        currentOrder.value = order.value.filter(item => {
            if (item.endTime === undefined || item.endTime === null) {
                return true
            }
        })
        historyOrder.value = order.value.filter(item => {
            if (item.endTime !== undefined && item.endTime !== null) {
                return true
            }
        })
        currentOrder.value = currentOrder.value.map(item => {
            let chager = null
            console.log(stations.value)
            for (let i = 0; i < stations.value.length; i++) {
                if (item.chargerId === stations.value[i].id) {
                    chager = stations.value[i]
                }
            }
            let power = chager.power
            let diff = dayjs(new Date()).diff(item.startTime, 'm')
            // 1 h 50 w 250 / 50 * 60 
            // TODO:// replace 250 with vehicle power
            let remain = (vbatterySize.value / power) * 60 - diff
            console.log(remain)
            item.predictTime = dayjs(new Date()).add(remain, 'm').format('YYYY-MM-DD HH:mm:ss')
            item.startTime = dayjs(item.startTime).format('YYYY-MM-DD HH:mm:ss')
            console.log(diff)
            return item
        })
        historyOrder.value = historyOrder.value.map(item => {
            item.startTime = dayjs(item.startTime).format('YYYY-MM-DD HH:mm:ss')
            item.endTime = dayjs(item.endTime).format('YYYY-MM-DD HH:mm:ss')
            return item
        })
    }
})

let endCharge = (row) => {
    let formData = new FormData()
    console.log(row)
    formData.append('userId', row.userId)
    formData.append('orderId', row.orderId)
    formData.append('chargerId', row.chargerId)
    formData.append('username', row.username)
    formData.append('chargerName', row.chargerName)
    formData.append('startTime', row.startTime)
    formData.append('endTime', dayjs(new Date()).format('YYYY-MM-DD HH:mm:ss'))
    formData.append('duration', dayjs(new Date()).diff(row.startTime, 'm'))
    formData.append('cost', dayjs(new Date()).diff(row.startTime, 'm') * 0.5)
    api({
        url: '/orders/finishorder',
        method: 'POST',
        data: formData
    }).then(res => {
        let response = res.data
        if (response.code === 200) {
            api({
                url: '/orders/getorder?user_id=' + info.value.userId,
                method: 'GET'
            }).then(res => {
                let response = res.data
                if (response.code === 200) {
                    order.value = response.data
                    currentOrder.value = order.value.filter(item => {
                        if (item.endTime === undefined || item.endTime === null) {
                            return true
                        }
                    })
                    historyOrder.value = order.value.filter(item => {
                        if (item.endTime !== undefined && item.endTime !== null) {
                            return true
                        }
                    })
                    currentOrder.value = currentOrder.value.map(item => {
                        let chager = null
                        console.log(stations.value)
                        for (let i = 0; i < stations.value.length; i++) {
                            if (item.chargerId === stations.value[i].id) {
                                chager = stations.value[i]
                            }
                        }
                        let power = chager.power
                        let diff = dayjs(new Date()).diff(item.startTime, 'm')
                        // 1 h 50 w 250 / 50 * 60 
                        // TODO:// replace 250 with vehicle power
                        let remain = (vbatterySize.value / power) * 60 - diff
                        console.log(remain)
                        item.predictTime = dayjs(new Date()).add(remain, 'm').format('YYYY-MM-DD HH:mm:ss')
                        item.startTime = dayjs(item.startTime).format('YYYY-MM-DD HH:mm:ss')
                        console.log(diff)
                        return item
                    })
                    historyOrder.value = historyOrder.value.map(item => {
                        item.startTime = dayjs(item.startTime).format('YYYY-MM-DD HH:mm:ss')
                        item.endTime = dayjs(item.endTime).format('YYYY-MM-DD HH:mm:ss')
                        return item
                    })
                }
            })
        }
    })



}


</script> 
 
<style scoped></style>