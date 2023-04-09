<template>
    <div class="tw-full tw-h-full">
        <div class="tw-flex tw-flex-col tw-justify-center tw-items-center tw-h-full">
            <div class="login">
                <el-card>
                    <h2 class="tw-text-center">登陆页面</h2>
                    <el-form class="login-form" >
                        <el-form-item>
                            <el-input v-model="username" placeholder="Username">
                                <template #prepend>
                                    <el-icon ><User /></el-icon>                                
                                </template>
                            </el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-input v-model="password" placeholder="Password" type="password">
                            <template #prepend>
                                    <el-icon ><Lock /></el-icon>                                
                                </template>
                            </el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button class="login-button" type="primary" @click="login" block>登陆</el-button>

                        </el-form-item>
                    </el-form>
                </el-card>
            </div>
        </div>
    </div>
</template>
 
<script setup>
import { ref } from 'vue'
import { api } from '../../boot/api'
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus'
import { User,Lock } from '@element-plus/icons-vue'
let username = ref('')
let password = ref('')
const router = useRouter();
let login = () => {
    let formData = new FormData()
    formData.append('username', username.value)
    formData.append('password', password.value)
    api.post('/user/login', formData).then(res => {
        let response = res.data
        if (response.code === 200) {
            sessionStorage.setItem('info', JSON.stringify(response.data))
            let role = response.role
            if (role === 'admin') {
                router.push('/admin')
            } else if (role === 'user') {
                router.push('/sys')
            }
            else if (role === 'repair') {
                router.push('/repair')
            }
        } else {
            ElMessage.error(response.msg)

        }
    })

}

</script> 
 
<style scoped>
.login {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
}

.login-button {
    width: 100%;
    margin-top: 40px;
}

.login-form {
    width: 290px;
}

.forgot-password {
    margin-top: 10px;
}
</style>