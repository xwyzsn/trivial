<template>
    <div class="tw-full tw-h-full">
        <div class="tw-flex tw-flex-col tw-justify-center tw-items-center tw-h-full">
            <div class="login" v-if="!registerModal">
                <el-card>
                    <h2 class="tw-text-center">登陆页面</h2>
                    <el-form class="login-form">
                        <el-form-item>
                            <el-input v-model="username" placeholder="Username">
                                <template #prepend>
                                    <el-icon>
                                        <User />
                                    </el-icon>
                                </template>
                            </el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-input v-model="password" placeholder="Password" type="password">
                                <template #prepend>
                                    <el-icon>
                                        <Lock />
                                    </el-icon>
                                </template>
                            </el-input>
                        </el-form-item>
                        <div class="tw-flex tw-justify-around">
                            <el-button class="login-button" type="primary" @click="login" block>登陆</el-button>
                            <el-button class="login-button" @click="registerModal = true" block>注册</el-button>
                        </div>
                    </el-form>
                </el-card>
            </div>
            <div class="login" v-else>
                <el-card>
                    <h2 class="tw-text-center">用户注册</h2>
                    <el-form class="login-form">
                        <el-form-item>
                            <el-input v-model="registerUsername" placeholder="输入用户名">
                                <template #prepend>
                                    <el-icon>
                                        <User />
                                    </el-icon>
                                </template>
                            </el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-input v-model="registerPassword" placeholder="输入密码" type="password">
                                <template #prepend>
                                    <el-icon>
                                        <Lock />
                                    </el-icon>
                                </template>
                            </el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-input v-model="confirmPassword" placeholder="确认密码" type="password">
                                <template #prepend>
                                    <el-icon>
                                        <Lock />
                                    </el-icon>
                                </template>
                            </el-input>
                        </el-form-item>
                        <div class="tw-flex tw-justify-around">
                            <el-button class="login-button" type="primary" @click="registerModal = false">返回</el-button>
                            <el-button class="login-button" @click="confirm" block>确认</el-button>
                        </div>
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
import { User, Lock } from '@element-plus/icons-vue'
let registerModal = ref(false)
let registerUsername = ref('')
let registerPassword = ref('')
let confirmPassword = ref('')
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
let confirm = () => {
    if (registerPassword.value !== confirmPassword.value) {
        ElMessage.error('两次密码不一致')
        return
    }
    let formData = new FormData()
    formData.append('username', registerUsername.value)
    formData.append('password', registerPassword.value)
    api.post('/user/add', formData).then(res => {
        let response = res.data
        if (response.code === 200) {
            ElMessage.success('注册成功')
            registerModal = false
        } else {
            ElMessage.error(response.data)

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
    width: 30%;
    margin-top: 40px;
}

.login-form {
    width: 290px;
}

.forgot-password {
    margin-top: 10px;
}
</style>