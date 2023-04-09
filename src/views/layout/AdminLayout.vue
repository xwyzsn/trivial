<template>
    <div class="tw-w-full tw-h-full">
       <el-container class="tw-w-full tw-h-full">
         <el-header class=" tw-h-14 tw-text-black tw-border-b-2 tw-border-gray-200 " style="background-color: #545c64;">
         </el-header>
         <el-container class="tw-w-full tw-h-full">
            <el-aside width="200px">
              <MyDrawer title="管理员导航栏" :data="drawerData"/>
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
    {name:'用户管理',path:'/admin/usermanage'},
    {name:'充电桩管理',path:'/admin/chargemanage'},
    {name:'订单管理',path:'/admin/ordermanage'},
    {name:'维修人员管理',path:'/admin/mantainmanage'},
    // {name:'个人中心',path:null,label:true},
    {name:'个人中心',path:'/admin/personmanage'},
    // {name:'分析模块',path:null,label:true},
    {name:'数据分析',path:'/admin/history'},
    {name:'报修记录',path:'/admin/report'},

])
   </script> 
    
    <style scoped>
   
   </style>