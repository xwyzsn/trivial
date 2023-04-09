<template>
    <div>
        <my-title color="red" content="个人信息"></my-title>
        <div class="tw-w-2/3  tw-mt-5">
            <el-form v-if="info" label-width="120px">
                <el-form-item label="头像">
                    <img :src="info.avatar" alt="">
                </el-form-item>
                <el-form-item label="用户名">
                    <el-input disabled v-model="info.username"></el-input>
                </el-form-item>
                <el-form-item label="昵称">
                    <el-input v-model="info.nickname"></el-input>
                </el-form-item>
                <el-form-item label="电话">
                    <el-input v-model="info.phone"></el-input>
                </el-form-item>
                <el-form-item label="邮箱">
                    <el-input v-model="info.email"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button class="tw-ml-5" type="primary" @click="updateInfo">修改信息</el-button>
                </el-form-item>

            </el-form>

        </div>
        <myTitle color="red" content="修改密码"></myTitle>
        <el-form label-width="120px" class="tw-w-2/3 tw-mt-5">
            <el-form-item label="原密码">
                <el-input type="password" v-model="oldPassword"></el-input>
            </el-form-item>
            <el-form-item label="新密码">
                <el-input type="password" v-model="newPassword"></el-input>
            </el-form-item>
            <el-form-item label="确认密码">
                <el-input type="password" v-model="confirmPassword"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="updatePassword">修改密码</el-button>
            </el-form-item>
        </el-form>
        <el-dialog v-model="showModal" :close-on-press-escape="false" :show-close="false" :close-on-click-modal="false">
            <template #title>
                <span>绑定车辆信息</span>
            </template>
            <el-form label-width="100px" >
                <el-form-item label="品牌" >
                    <el-input v-model="showData.brand"></el-input>
                </el-form-item>
                <el-form-item label="型号">
                    <el-input v-model="showData.model"></el-input>
                </el-form-item>
                <el-form-item label="电池大小">
                    <el-input-number  v-model="showData.batterySize"></el-input-number>
                </el-form-item>
                <el-form-item label="充电瓦数">
                    <el-input-number v-model="showData.chargingPower"></el-input-number>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="bindCar">确 定</el-button>
            </span>

        </el-dialog>
    </div> 
 </template>
 
 <script setup>
    import myTitle from '../../components/MyTitle.vue'
    import {api} from '../../boot/api'
    import {ElMessage} from 'element-plus'
    import {ref} from 'vue'
    let info = ref(JSON.parse(sessionStorage.getItem('info')))
    let oldPassword = ref('')
    let newPassword = ref('')
    let confirmPassword = ref('')
    let cars = ref(null)
    let showModal = ref(false)
    let showData = ref({
        brand:'',
        model:'',
        batterySize:'',
        chargingPower:''
    })
    let updatePassword = ()=>{
        if(oldPassword.value === ''){
            ElMessage.error('请输入原密码')
            return
        }
        if(newPassword.value === ''){
            ElMessage.error('请输入新密码')
            return
        }
        if(confirmPassword.value === ''){
            ElMessage.error('请输入确认密码')
            return
        }
        if(oldPassword.value != info.value.password){
            ElMessage.error('原密码错误')
            return
        }
        if(newPassword.value !== confirmPassword.value){
            ElMessage.error('两次密码不一致')
            return
        }
        let formData = new FormData()
        formData.append('password',newPassword.value)
        formData.append('username',info.value.username)
        api({
            url:'/user/update',
            data:formData,
            method:'POST'
        }).then(res=>{
            let response = res.data
            if(response.code === 200){
                ElMessage.success(response.msg)
            }else{
                ElMessage.error(response.msg)
            }
            api('/user/checkuser?username='+info.value.username).then(res=>{
                let response = res.data
                if(response.code === 200){
                    sessionStorage.setItem('info',JSON.stringify(response.data))
                    info.value = response.data
                }
            })
        })
    }
    let updateInfo = ()=>{
        let formData = new FormData()
        formData.append('nickname',info.value.nickname)
        formData.append('phone',info.value.phone)
        formData.append('email',info.value.email)
        formData.append('username',info.value.username)

        api({
            url:'/user/update',
            data:formData,
            method:'POST'
        }).then(res=>{
            let response = res.data
            if(response.code === 200){
                ElMessage.success(response.msg)
            }else{
                ElMessage.error(response.msg)
            }
            api('/user/checkuser?username='+info.value.username).then(res=>{
                let response = res.data
                if(response.code === 200){
                    sessionStorage.setItem('info',JSON.stringify(response.data))
                    info.value = response.data
                }
            })
        })
    }
    let getStation = ()=>{
        api('/chargingStation/getall').then(res=>{
            let response = res.data
            if(response.code === 200){
                sessionStorage.setItem('station',JSON.stringify(response.data))
            }
        })
    }
    getStation()
    api({ url: '/vehicle/getcar?user_id=' + info.value.userId, method: 'GET' }).then(res => {
        let response = res.data
        if (response.code === 200) {
            cars.value = response.data
        }
        if(cars.value.length === 0){
            ElMessage.info('您还没有添加车辆，请先添加车辆')
            showModal.value = true
        }
    })
    let bindCar = ()=>{
        let formData = new FormData()
        formData.append('brand',showData.value.brand)
        formData.append('model',showData.value.model)
        formData.append('batterySize',showData.value.batterySize)
        formData.append('wattage',showData.value.chargingPower)
        formData.append('userId',info.value.userId)
        api('/vehicle/addcar',{
            data:formData,
            method:'POST'
        }).then(res=>{
            let response = res.data
            if(response.code === 200){
                ElMessage.success(response.msg)
                showModal.value = false
                api({ url: '/vehicle/getcar?user_id=' + info.value.userId, method: 'GET' }).then(res => {
                    let response = res.data
                    if (response.code === 200) {
                        cars.value = response.data
                    }
                })
            }else{
                ElMessage.error(response.msg)
            }
        })
    }
</script> 
 
 <style scoped>
 
</style>