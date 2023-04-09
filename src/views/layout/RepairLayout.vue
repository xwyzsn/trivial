<template>
    <div class="tw-w-full tw-h-full">
       <el-container class="tw-w-full tw-h-full">
         <el-header class=" tw-h-14 tw-text-black tw-border-b-2 tw-border-gray-200 " style="background-color: #545c64;">
         </el-header>
         <el-container class="tw-w-full tw-h-full">
            <el-aside width="200px">
              <MyDrawer title="维修人员导航栏" :data="drawerData"/>
            </el-aside>
            <el-main>
              <router-view />
            </el-main>
    </el-container>
       </el-container>
    </div>
    </template>
    
<script setup>
import MyDrawer from '../../components/MyDrawer.vue';
import { ref } from 'vue';
let drawerData = ref([
    // {name:'管理模块',path:null,label:true},
    {name:'个人中心',path:'/repair/index'},
    {name:'保修管理',path:'/repair/order'},
    {name:'订单历史',path:'/repair/history'}

])
   </script> 
    
<style scoped>
   
</style>