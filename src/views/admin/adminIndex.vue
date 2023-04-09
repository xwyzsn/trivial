<template>
 <div class="tw-w-full tw-h-full">
    <el-container class="tw-w-full tw-h-full">
            <el-aside class="tw-bg-red-400" width="200px">
              <MyDrawer title="管理员导航栏" :data="drawerData"/>
            </el-aside>
            <el-main>
              <router-view />
            </el-main>
    </el-container>
 </div>
 </template>
 
 <script setup>
import MyDrawer from '../../components/MyDrawer.vue';
import { ref } from 'vue';
let drawerData = ref([
    // {name:'管理模块',path:null,label:true},
    {name:'用户管理',path:'/admin/user'},
    {name:'充电桩管理',path:'/admin/goods'},
    {name:'订单管理',path:'/admin/order'},
    {name:'维修人员管理',path:'/admin/comment'},
    // {name:'个人中心',path:null,label:true},
    {name:'个人中心',path:'/admin/info'},
    // {name:'分析模块',path:null,label:true},
    {name:'数据分析',path:'/admin/userAnalysis'},

])

</script> 
 
 <style scoped>
 
</style>