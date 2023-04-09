<template>
 <div>
        <MyTitle class="tw-p-4 tw-m-3 "  content="公告"></MyTitle>
        <div class="tw-p-4 tw-m-3">
            <el-card class="tw-m-4" v-for="item in data" :key="item.id">
                <div slot="header" class="tw-text-xl tw-font-medium  tw-text-gray-900 ">{{item.title}}</div>
                <div class="tw-text-gray-500 tw-text-sm tw-mb-2">{{item.createTime}}</div>
                <div class="tw-text-gray-500 tw-text-sm tw-mb-2">{{item.content}}</div>
            </el-card>
        </div>

 </div>
 
 </template>
 
 <script setup>
    import MyTitle from '../../components/MyTitle.vue';
    import {ref} from 'vue';
    import { api } from '../../boot/api';
    let data = ref([]);
    api({
        url:'/announcement/getall',
    }).then(res=>{
       data.value = res.data.data;
    })
 
</script> 
 
 <style scoped>
 
</style>