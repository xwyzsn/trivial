<template>
 <div class="tw-w-full  tw-border-sky-300">
    <blockquote class="tw-p-2 tw-ml-0 border tw-border-sky-400 tw-bg-gray-200" >
    <p class="tw-text-xl tw-font-medium  tw-text-gray-900 ">
        {{ content  }}
    </p>
    </blockquote>
 </div> 
 </template>
 
 <script setup>
 import { defineProps } from 'vue';
 const props = defineProps({
   color: {
     type: String,
   },
   content:{
        type: String,
        default: ''
   }
 });
 
</script> 
 
 <style scoped>
 .border{
   border-left:  4px solid;
 }
</style>