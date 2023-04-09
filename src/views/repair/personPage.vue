<template>
 <div class="tw-w-full tw-h-full">
    <MyTitle content="个人中心"/>

    <div class="tw-w-5/6 tw-m-auto">
        <el-form label-width="100px" >
            <el-form-item label="用户名">
                <el-input v-model="info.username" ></el-input>
            </el-form-item>
            <el-form-item label="密码">
                <el-input v-model="info.password" ></el-input>
            </el-form-item>
            <el-form-item label="昵称">
                <el-input v-model="info.name" ></el-input>
            </el-form-item>
            <el-form-item label="邮箱">
                <el-input v-model="info.email" ></el-input>
            </el-form-item>
            <el-form-item label="电话">
                <el-input v-model="info.phone" ></el-input>
            </el-form-item>
        </el-form>
        <div class="tw-flex tw-flex-row-reverse  tw-mr-20 tw-mt-10">
            <el-button type="primary" @click="confirmUpdate">提交</el-button>
            <el-button @click="" >重置</el-button>
        </div>
    </div>
 </div>
 </template>
 
 <script setup>
 import {ref} from 'vue' 
import { api } from '../../boot/api';
 import MyTitle from '../../components/MyTitle.vue';
 import { ElMessage } from 'element-plus';
 let info = ref(JSON.parse(sessionStorage.getItem('info')))
let confirmUpdate = ()=>{
    let formData = new FormData();
    formData.append('id',info.value.id)
    formData.append('username',info.value.username)
    formData.append('password',info.value.password)
    formData.append('name',info.value.name)
    formData.append('email',info.value.email)
    formData.append('phone',info.value.phone)
    api({
        url:'/repairPerson/update',
        method:'post',
        data:formData
    }).then(res=>{
        if(res.data.code == 200){
            ElMessage.success('修改成功')
            sessionStorage.setItem('info',JSON.stringify(info.value))
        }else{
            ElMessage.error('修改失败')
        }
    })
}
</script> 
 
 <style scoped>
 
</style>