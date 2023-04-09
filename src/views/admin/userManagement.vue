<template>
    <div class="tw-w-full tw-h-full">
        <MyTitle content="用户管理" />
        <el-button type="primary" size="lg" @click="addNewModal=true">新增</el-button>
        <el-table
        v-if="data"
        :data="data"
        class="tw-w-full tw-h-full"
        >
        <el-table-column prop="nickname" label="昵称" >
        </el-table-column>
        <el-table-column prop="password" label="密码"></el-table-column>
        <el-table-column prop="phone" label="手机"></el-table-column>
        <el-table-column prop="username" label="用户名"></el-table-column>
        <el-table-column prop="email" label="邮件"></el-table-column>
        <el-table-column fixed="right" label="Operations" width="120">
      <template #default="scope">
        <el-button link type="primary" size="small" @click="handleClick(scope.$index,scope.row)">编辑</el-button>
        <el-button link type="primary" size="small" @click="confirmDelte(scope.$index,scope.row)">删除</el-button>
      </template>
    </el-table-column>
        </el-table>
        <el-dialog v-model="showModal"  class="tw-space-y-3" title="用户信息" width="30%" :before-close="handleClose">
            <el-form label-width="100px">
                <el-form-item label="用户名">
                    <el-input v-model="showData.username"  placeholder="用户名"></el-input>
                </el-form-item>
                <el-form-item label="昵称">
                    <el-input v-model="showData.nickname" placeholder="昵称"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="showData.password" placeholder="密码"></el-input>
                </el-form-item>
                <el-form-item label="手机">
                    <el-input v-model="showData.phone" placeholder="手机"></el-input>
                </el-form-item>

                <el-form-item label="邮件">
                    <el-input v-model="showData.email" placeholder="邮件"></el-input>
                </el-form-item>

            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="()=>{showModal = false;showData = {}}">取消</el-button>
                    <el-button type="primary" @click="updateConfirm">
                        确认
                    </el-button>
                </span>
            </template>
        </el-dialog>

        <el-dialog v-model="addNewModal"  class="tw-space-y-3" title="用户信息" width="30%" :before-close="handleClose">
            <el-form label-width="100px">
                <el-form-item label="用户名">
                    <el-input v-model="newData.username"  placeholder="用户名"></el-input>
                </el-form-item>
                <el-form-item label="昵称">
                    <el-input v-model="newData.nickname" placeholder="昵称"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="newData.password" placeholder="密码"></el-input>
                </el-form-item>
                <el-form-item label="手机">
                    <el-input v-model="newData.phone" placeholder="手机"></el-input>
                </el-form-item>

                <el-form-item label="邮件">
                    <el-input v-model="newData.email" placeholder="邮件"></el-input>
                </el-form-item>

            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="()=>{showModal = false;showData = {}}">取消</el-button>
                    <el-button type="primary" @click="addConfirm">
                        确认
                    </el-button>
                </span>
            </template>
        </el-dialog>

    </div>  
  
 </template>
 
 <script setup>
 import { ref } from 'vue';
import { api } from '../../boot/api';
import MyTitle from '../../components/MyTitle.vue';
import {ElMessage} from 'element-plus'
 let data = ref([])
 let showData = ref({})
 let showModal = ref(false)
 let newData = ref({
        username:'',
        nickname:'',
        password:'',
        phone:'',
        email:''
 })
 let addNewModal = ref(false)
 api({url:'/user/getall'}).then(res=>{
     let response = res.data
     if(response.code === 200){
         data.value = response.data
         console.log(data.value)
     }})
let handleClick = (index,row)=>{
    showData.value = row
    showModal.value = true
}
let updateConfirm = ()=>{
    let formData = new FormData()
    formData.append('userId',showData.value.userId)
    formData.append('username',showData.value.username)
    formData.append('nickname',showData.value.nickname)
    formData.append('password',showData.value.password)
    formData.append('phone',showData.value.phone)
    formData.append('email',showData.value.email)
    api({
        url:'/user/checkuser?username='+showData.value.username,
        method:'get'
    }).then(res=>{
        let response = res.data 
        if(response.code === 200 && response.data.userId !== showData.value.userId ){
            ElMessage({
                message: '用户名已存在',
                type: 'error'
            })
        }else{
            api({
                url:'/user/update',
                method:'post',
                data:formData
            }).then(res=>{
                let response = res.data
                if(response.code === 200){
                    ElMessage({
                        message: '修改成功',
                        type: 'success'
                    })
                    showModal.value = false
                    showData.value = {}
                }else{
                    ElMessage({
                        message: '修改失败',
                        type: 'error'
                    })
                }
            })
        }
    })
    }
let confirmDelte = (index,row)=>{
    let delteUserId = row.userId
    api({
        url:'/user/delete?userId='+delteUserId,
        method:'delete'
    }).then(res=>{
        let response = res.data
        if(response.code === 200){
            ElMessage({
                message: '删除成功',
                type: 'success'
            })
            data.value.splice(index,1)
        }else{
            ElMessage({
                message: '删除失败',
                type: 'error'
            })
        }
    })
}
let addConfirm = ()=>{
    let formData = new FormData()
    formData.append('username',newData.value.username)
    formData.append('nickname',newData.value.nickname)
    formData.append('password',newData.value.password)
    formData.append('phone',newData.value.phone)
    formData.append('email',newData.value.email)
    api({
        url:'/user/checkuser?username='+newData.value.username,
        method:'get'
    }).then(res=>{
        let response = res.data 
        if(response.code === 200){
            ElMessage({
                message: '用户名已存在',
                type: 'error'
            })
        }else{
            api({
                url:'/user/add',
                method:'post',
                data:formData
            }).then(res=>{
                let response = res.data
                if(response.code === 200){
                    ElMessage({
                        message: '添加成功',
                        type: 'success'
                    })
                    addNewModal.value = false
                    newData.value = {}
                }else{
                    ElMessage({
                        message: '添加失败',
                        type: 'error'
                    })
                }
            }).then(()=>{
                api({url:'/user/getall'}).then(res=>{
                    let response = res.data
                    if(response.code === 200){
                        data.value = response.data
                        console.log(data.value)
                    }})
            })
        }
    })
}
 
</script> 
 
 <style scoped>

 
</style>