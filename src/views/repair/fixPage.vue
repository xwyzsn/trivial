<template>
 <div class="tw-h-full tw-w-full">
    <MyTitle content="保修管理"/>
    <div class="tw-w-full tw-m-auto">
        <el-table :data="data" style="width: 100%">
            <el-table-column prop="id" label="ID" width="180">
            </el-table-column>
            <el-table-column prop="stationName" label="姓名" width="180">
            </el-table-column>
            <el-table-column prop="description" label="描述" width="180">
            </el-table-column>
            <el-table-column prop="address" label="地址" width="180">
            </el-table-column>
            <el-table-column prop="reportTime" label="上报时间" width="180">
            </el-table-column>
            <el-table-column prop="status" label="状态" width="180">
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="180">
                <template #default="scope">
                    <el-button type="primary" size="mini" @click="handleEdit(scope.$index, scope.row)">修复</el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
    <el-dialog v-model="showModal">
        <el-form label-width="100px">
            <el-form-item label="维修时间">
                <el-date-picker v-model="showData.finishTime" type="datetime" placeholder="选择日期时间"></el-date-picker>
            </el-form-item>
            <el-form-item label="维修费用">
                <el-input-number v-model="showData.cost" ></el-input-number>
            </el-form-item>
            <el-form-item label="维修描述">
                <el-input v-model="showData.description" ></el-input>
            </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button @click="showModal = false">取 消</el-button>
            <el-button type="primary" @click="commit()">确 定</el-button>
        </span>

    </el-dialog>

 </div>
 </template>
 
 <script setup>
 import {ref} from 'vue' 
 import MyTitle from '../../components/MyTitle.vue';
import { api } from '../../boot/api';
import dayjs from 'dayjs'
import { ElMessage } from 'element-plus';
let data = ref([])
let showModal = ref(false)
let showData = ref({
    finishTime:'',
    cost:0.0,
    description:'',
    id:'',
    address:'',
    stationName:''
})
let handleEdit = (index,row)=>{
    showModal.value = true
    showData.value.id = row.id
    showData.value.address = row.address
    showData.value.stationName = row.stationName
    console.log(showData.value)
}
let commit = ()=>{
    let formData = new FormData()
    formData.append('finishTime',dayjs(showData.value.finishTime).format('YYYY-MM-DD HH:mm:ss'))
    formData.append('cost',showData.value.cost)
    formData.append('description',showData.value.description)

    let info = JSON.parse(sessionStorage.getItem('info'))
    console.log(info)
    formData.append('maintenanceStaffId',+info.id)
    formData.append('id',+showData.value.id)
    formData.append('maintenanceStaffName',info.username)
    formData.append('address',showData.value.address)
    formData.append('stationName',showData.value.stationName)
    api({
        url:'/maintenanceRecords/finish',
        method:'post',
        data:formData
    }).then(res=>{
        let reponse = res.data
        if(reponse.code == 200){
            showModal.value = false
            data.value = reponse.data
            ElMessage({
                message: '维修成功',
                type: 'success'
            });
            showModal.value = false
            showData.value = {
                finishTime:'',
                cost:0.0,
                description:'',
                id:'',
                address:'',
                stationName:''
            }
        }else{
            ElMessage({
                message: '维修失败',
                type: 'error'
            });
        }
    })
}
api({
    url:'/maintenanceRecords/getnotavail',
    method:'get',
}).then(res=>{
    data.value = res.data.data
    console.log(data.value);
})

</script> 
 
 <style scoped>
 
</style>