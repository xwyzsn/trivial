<template>
<div class="tw-h-full tw-w-full">
    <el-menu 
       class="tw-h-full tw-w-full"
            :default-active="activeIndex"
            background-color="#545c64"
            text-color="#fff"
            :title="title"
            router
    >
    <el-menu-item :index="idx.toString()" v-for="(item,idx) in data" :key="idx" :route="item.path">
        <template #title v-if="item.label===null || item.label === undefined">
            <i class="el-icon-location"></i>
            <span>{{item.name}}</span>
        </template>
    </el-menu-item>
    </el-menu>



</div>
 </template>
 
 <script setup>
import { defineProps } from 'vue';
 import {ref} from 'vue'
 import { useRouter } from 'vue-router';
 const router = useRouter();
 let path = router.currentRoute.value.path
 const props = defineProps({
   title: {
     type: String,
     default: 'My Drawer',
   },
   data:{
        type: Array,
        default: []
   },
   subMenu:{
    type:Boolean,
    default:false
   }
 });

 let activeIndex = ref('0')
 props.data.forEach((item,idx)=>{
     if(item.path === path){
         activeIndex.value = idx.toString()
     }
 })
</script> 
 
 <style scoped>
 
</style>