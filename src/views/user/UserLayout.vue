<template>
       <div class="tw-w-full tw-h-full">
           <el-container class="tw-w-full tw-h-full">
            <el-aside class="tw-bg-red-400" width="200px">
              <MyDrawer :data="sider" />
            </el-aside>
            <el-main>
              <router-view />
            </el-main>
            </el-container>
       </div>
 </template>
 
 <script setup>
 import {ref} from 'vue'
import { useRoute } from 'vue-router'
import MyDrawer from '../../components/MyDrawer.vue';
let sider = ref([
    {name:'个人中心',path:'/sys/user/index'},
    {name:'我的订单',path:'/sys/user/order'},
    {name:'我的车辆',path:'/sys/user/car'},
    {name:'钱包',path:'/sys/user/wallet'},
    {name:'意见反馈',path:'/sys/user/feedback'}
])
 
</script> 
 
 <style scoped>
 
</style>