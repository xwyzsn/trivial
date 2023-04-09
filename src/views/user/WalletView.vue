<template>
    <my-title content="我的钱包"></my-title>
    <div class="tw-grid tw-grid-cols-4">
    <div class="tw-border tw-shadow tw-rounded-lg tw-m-4 tw-h-full">
        <el-statistic class="tw-m-4 " title="余额剩余" :value="money">
            <template #suffix>
            </template>
          </el-statistic>
    </div>

    </div> 
    <div class="tw-mt-4">
        <el-button type="primary" class="tw-m-4" @click="showModal = true">充值</el-button>
    </div>
    <el-dialog v-model="showModal">
        <template #title>
            <span>充值</span>
        </template>
        <el-form label-width="100px" >
            <el-form-item label="充值金额">
                <el-input-number v-model="showData.money"></el-input-number>
            </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer tw-flex tw-flex-row-reverse" >
            <el-button @click="showModal = false">取 消</el-button>
            <el-button type="primary" @click="addMoney">确 定</el-button>
          </span>

    </el-dialog>
 </template>
 
 <script setup>
 import myTitle from '../../components/MyTitle.vue'
 import {ref} from 'vue'
 import {api} from '../../boot/api'
import {ElMessage} from 'element-plus'
 let info  = ref(JSON.parse(sessionStorage.getItem('info')))
 let money = ref(0.00)
 let showData = ref({
        money:''
    })
 let showModal = ref(false)
 let getMoney = ()=>{
        api.get('/wallet/get?userId='+info.value.userId).then(res=>{
            money.value = res.data.data.money
        })
 }
 getMoney()

 let addMoney = ()=>{
     let money = showData.value.money
     let formData = new FormData()
        formData.append('num',money)
        formData.append('userId',info.value.userId)
        formData.append('method','add')
        api.post('/wallet/update',formData).then(res=>{
            if(res.data.code === 200){
                ElMessage.success('充值成功')
                showModal.value = false
            }
            else{
                ElMessage.error('充值失败')
            }
            getMoney()
        })

 }
</script> 
 
 <style scoped>
 
</style>