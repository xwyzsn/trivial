<template>
 <div class="tw-w-full tw-h-full">
    <el-table v-if="stations" 
      class="tw-h-1/2"
     :data="stations.slice((currentPage-1)*pageSize,currentPage*pageSize)"
     :default-sort="{ prop: 'status', order: 'descending' }" 
      style="max-height:500px;overflow: auto;">
        <el-table-column prop="stationName" label="站点名字"></el-table-column>
        <el-table-column prop="description" label="故障信息"></el-table-column>
        <el-table-column prop="status" label="修复状态" sortable></el-table-column>
        <el-table-column prop="address" label="站点地址"></el-table-column>
        <el-table-column prop="reportTime" label="上报时间"></el-table-column>
        <el-table-column prop="finishTime" label="修复时间"></el-table-column>
        <el-table-column prop="maintenanceStaffName" label="修复人员"></el-table-column>
        <el-table-column prop="cost" label="花费"></el-table-column>

    </el-table>
    <div class="tw-flex tw-mt-10 tw-flex-row-reverse tw-mr-10">
        <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[3,50, 100]"
          :small="small"
          :disabled="disabled"
          :background="background"
          layout="sizes, prev, pager, next"
          :total="stations.length"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
    </div>


 </div> 
 </template>
 
 <script setup>
import {ref} from 'vue' 
import { api } from '../../boot/api';
let currentPage = ref(1)
let pageSize = ref(3)

let stations = ref([])
api({
    url: '/maintenanceRecords/getall',
    method: 'get'
}).then(res => {
    stations.value = res.data.data
    
})
</script> 
 
 <style scoped>
 
</style>