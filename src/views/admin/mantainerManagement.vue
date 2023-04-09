<template>
    <div class="tw-w-full tw-h-full">
        <MyTitle  content="维修人员管理"/>
        <el-button type="primary" @click="addNewModal = true">添加新维修人员</el-button>

        <el-table v-if="data" :data="data">
            <el-table-column prop="username" label="用户名"></el-table-column>
            <el-table-column prop="password" label="密码"></el-table-column>
            <el-table-column prop="name" label="昵称"></el-table-column>
            <el-table-column prop="email" label="邮箱"></el-table-column>
            <el-table-column prop="phone" label="手机号"></el-table-column>
            <el-table-column label="操作">
                <template #default="scope">
                    <el-button link type="primary" size="small" @click="handleClick(scope.$index,scope.row)">编辑</el-button>
                    <el-button link type="primary" size="small" @click="confirmDelte(scope.$index,scope.row)">删除</el-button>
                </template>
            </el-table-column>

        </el-table>
        <el-dialog v-model="showModal">
            <el-form label-width="100px">
                <el-form-item label="用户名">
                    <el-input v-model="showData.username" placeholder="用户名"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="showData.password" placeholder="密码"></el-input>
                </el-form-item>
                <el-form-item label="昵称">
                    <el-input v-model="showData.name" placeholder="昵称"></el-input>
                </el-form-item>
                <el-form-item label="邮箱">
                    <el-input v-model="showData.email" placeholder="邮箱"></el-input>
                </el-form-item>
                <el-form-item label="手机号">
                    <el-input v-model="showData.phone" placeholder="手机号"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="showModal = false">取 消</el-button>
                <el-button type="primary" @click="updateConfirm">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog v-model="addNewModal">
            <el-form label-width="100px">
                <el-form-item label="用户名">
                    <el-input v-model="newData.username" placeholder="用户名"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="newData.password" placeholder="密码"></el-input>
                </el-form-item>
                <el-form-item label="昵称">
                    <el-input v-model="newData.name" placeholder="昵称"></el-input>
                </el-form-item>
                <el-form-item label="邮箱">
                    <el-input v-model="newData.email" placeholder="邮箱"></el-input>
                </el-form-item>
                <el-form-item label="手机号">
                    <el-input v-model="newData.phone" placeholder="手机号"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="addNewModal = false">取 消</el-button>
                <el-button type="primary" @click="addConfirm">确 定</el-button>
            </div>
        </el-dialog>

    </div>
 </template>
 
 <script setup>
 import { ref } from 'vue';
import { api } from '../../boot/api.js';
import {ElMessage} from 'element-plus'
import MyTitle from '../../components/MyTitle.vue'
let data = ref([])
let showData = ref({})
let showModal = ref(false)
let addNewModal = ref(false)
let newData = ref({})
 api({
    url:'/repairPerson/getall'
 }).then(res=>{
    data.value = res.data.data
 })
    function handleClick(index,row){
        showData.value = row
        showModal.value = true
    }
    function confirmDelte(index,row){
        api({
            url:'/repairPerson/delete?id='+row.id,
            method:'delete',
        }).then(res=>{
            if(res.data.code === 200){
                ElMessage.success('删除成功')
                data.value.splice(index,1)
            }else{
                ElMessage.error('删除失败')
            }
        })
    }
    function updateConfirm(){
        let formData = new FormData()
        for(let key in showData.value){
            formData.append(key,showData.value[key])
        }
        formData.append('id',showData.value.id)
        api({
            url:'/repairPerson/update',
            method:'post',
            data:formData
        }).then(res=>{
            if(res.data.code === 200){
                ElMessage.success('修改成功')
                showModal.value = false
            }else{
                ElMessage.error('修改失败')
            }
        })
    }
    function addConfirm(){
        let formData = new FormData()
        for(let key in newData.value){
            formData.append(key,newData.value[key])
        }
        api({
            url:'/repairPerson/add',
            method:'post',
            data:formData
        }).then(res=>{
            if(res.data.code === 200){
                ElMessage.success('添加成功')
                addNewModal.value = false
                api({
                    url:'/repairPerson/getall'
                }).then(res=>{
                    data.value = res.data.data
                })
            }else{
                ElMessage.error('添加失败')
            }
        })
    }
 
</script> 
 
 <style scoped>
 
</style>
