<template>
    <div class="tw-w-full tw-h-full">
        <MyTitle content="意见反馈"></MyTitle>
        <el-form>
            <el-form-item label="你的邮箱">
                <el-input></el-input>
            </el-form-item>
            <el-form-item label="输入内容">
                <el-input type="textarea" placeholder="请输入内容"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary">提交</el-button>
            </el-form-item>
        </el-form>
        <hr class="tw-gray-400">
        <MyTitle content="联系客服"></MyTitle>
        <el-descriptions title="客服信息">
            <el-descriptions-item label="客服电话"><el-tag>18100000000</el-tag></el-descriptions-item>
            <el-descriptions-item label="工作时间"><el-tag>8：00-18：00</el-tag></el-descriptions-item>
            <el-descriptions-item label="工作地点">
                <el-tag size="small">苏州</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="邮箱">
                <el-tag size="small">XXX@qq.com</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="地点">
                <el-tag size="small">
                No.1188, Wuzhong Avenue, Wuzhong District, Suzhou, Jiangsu
                Province
                </el-tag>
            </el-descriptions-item>
        </el-descriptions>


    </div>
</template>
 
<script setup>
import MyTitle from '../../components/MyTitle.vue';


</script> 
 
<style scoped></style>