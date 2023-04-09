<template>
    <div class="tw-w-full tw-h-full">
        <MyTitle  content="个人管理"/>
        <div>
            <el-form label-width="100px">
                <el-form-item label="用户名">
                    <el-input v-model="person.username" placeholder="用户名"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="person.password" placeholder="密码"></el-input>
                </el-form-item>
                <el-form-item label="昵称">
                    <el-input v-model="person.name" placeholder="昵称"></el-input>
                </el-form-item>
                <el-form-item label="邮箱">
                    <el-input v-model="person.email" placeholder="邮箱"></el-input>
                </el-form-item>
                <el-form-item label="手机号">
                    <el-input v-model="person.phone" placeholder="手机号"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="showModal = false">取 消</el-button>
                <el-button type="primary" @click="updateConfirm">确 定</el-button>
            </div>
        </div>

     
    </div>
 </template>
 
 <script setup>
import MyTitle from '../../components/MyTitle.vue';
import { ref } from 'vue';
import { api } from '../../boot/api.js';
import {ElMessage} from 'element-plus'
let info = JSON.parse(sessionStorage.getItem('info'))

let person = ref({
    username: info.username,
    password: info.password,
    name: info.name,
    email: info.email,
    phone: info.phone,
    id: info.id
})
let updateConfirm = () =>{
    let formData = new FormData()
    formData.append('username',person.value.username)
    formData.append('password',person.value.password)
    formData.append('name',person.value.name)
    formData.append('email',person.value.email)
    formData.append('phone',person.value.phone)
    formData.append('id',+info.id)
    api({
        url:'/admins/update',
        method:'post',
        data:formData
    }).then(res=>{
        let response = res.data
        if(response.code == 200){
            ElMessage.success(response.msg)
            sessionStorage.setItem('info',JSON.stringify(person.value))
        }else{
            ElMessage.error(response.msg)
        }

    })
}
</script> 
 
 <style scoped>
 
</style>
