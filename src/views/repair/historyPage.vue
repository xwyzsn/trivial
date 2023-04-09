<template>
 <div class="tw-w-full tw-h-full">
    <el-table v-if="history" :data="history">
        <el-table-column prop="stationName" label="充电桩名称"></el-table-column>
        <el-table-column prop="description" label="问题描述"></el-table-column>

        <el-table-column prop="reportTime" label="上报时间"></el-table-column>
        <el-table-column prop="finishTime" label="修复时间"></el-table-column>
        <el-table-column prop="cost" label="金额"> </el-table-column>
    </el-table>
    
 </div>
 </template>
 
 <script setup>
 import {ref} from 'vue' 
 import {api} from '../../boot/api'
 let info = JSON.parse(sessionStorage.getItem('info'))
 let history =  ref([])
 api({
    url:'/maintenanceRecords/gethistory?'+new URLSearchParams({staffId:info.id}),
    method:'get',
 }).then(res=>{
    history.value = res.data.data
    console.log(history.value)
 })

</script> 

 
 <style scoped>
 
</style>