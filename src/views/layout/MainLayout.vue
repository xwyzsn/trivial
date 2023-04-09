<template>
 <div class="tw-w-full tw-h-full">
    <el-container class="tw-w-full tw-h-full">
      <el-header class=" tw-h-14 tw-text-black tw-border-b-2 tw-border-gray-200 " style="background-color: #545c64;">
        <div class=" tw-mt-4 tw-flex tw-justify-center tw-space-x-10 "  >
          <div  >
            <RouterLink   to="/sys/user" class="tw-m-4 tw-text-gray-400 hover:tw-text-blue-500   tw-w-14 tw-h-8 tw-text-center"
            active-class=" tw-border-b-2 active-text-color tw-border-blue-500 tw-mb-4"
              >
                个人信息
            </RouterLink>
          </div>
          <div >
            <RouterLink  to="/sys/index" class="tw-m-4 tw-text-gray-400  hover:tw-text-blue-500   tw-w-14 tw-h-8 tw-text-center"
            active-class=" tw-border-b-2 active-text-color tw-border-blue-500  	"
              >
                首页
            </RouterLink>
          </div>
          <div >
            <RouterLink  to="/sys/annouce" class="tw-m-4 tw-text-gray-400  hover:tw-text-blue-500   tw-w-14 tw-h-8 tw-text-center"
            active-class=" tw-border-b-2 active-text-color tw-border-blue-500  	"
              >
              新闻公告
            </RouterLink>
          </div>
          <!-- <div >
            <RouterLink  to="/sys/wallet" class="tw-m-4 tw-text-gray-400  hover:tw-text-blue-500   tw-w-14 tw-h-8 tw-text-center"
            active-class=" tw-border-b-2 active-text-color tw-border-blue-500"
              >
              钱包
            </RouterLink>
          </div> -->
        </div>
      </el-header>
      <RouterView />
    </el-container>
 </div>
 </template>
 
 <script setup>

 
</script> 
 
 <style scoped>
 .header{
  margin: auto;
  width: 70%;
 }
 a {
    text-decoration: none;
  }
 
  .router-link-active {
    text-decoration: none;
  }
  .active-text-color {

color: #3370ff !important;
}


</style>