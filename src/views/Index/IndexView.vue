<template>
    <div class="tw-w-full tw-h-full">
        <div class="tw-w-2/3 tw-m-auto tw-mt-10 tw-h-1/3">
            <el-carousel class="tw-w-full" indicator-position="outside">
                <el-carousel-item v-for="item in 4" :key="item">
                    <h3 text="2xl" justify="center">{{ item }}</h3>
                </el-carousel-item>
            </el-carousel>
        </div>
        <div class="tw-w-2/3 tw-m-auto tw-h-2/3 tw-mt-40 ">
            <div v-if="stations" class="tw-w-full tw-h-full tw-grid tw-gap-5 tw-grid-cols-3">
                <div v-for="(item, idx) in stations" :key="idx">
                    <div class="tw-border tw-w-full tw-h-full tw-shadow tw-rounded-lg tw-cursor-pointer"
                        @click="() => { handleClicked(item) }">
                        <div class="tw-w-full tw-h-1/3 tw-bg-gray-200 tw-rounded-t-lg">
                            <div
                                class="tw-w-full tw-h-full tw-mt-4 tw-pl-2 tw-pt-5 tw-rounded-t-lg tw-text-2xl tw-font-bold tw-text-gray-700">
                                {{ item.name }}
                            </div>
                        </div>
                        <div class="tw-w-full tw-h-2/3 tw-p-5">
                            <div class="tw-w-full tw-h-1/3 tw-text-lg tw-text-gray-500">
                                <span>地址：</span> {{ item.address }}
                            </div>
                            <div class="tw-w-full tw-h-1/3 tw-text-lg tw-text-gray-500">
                                <span>瓦数：</span> {{ item.power }}
                            </div>
                            <div v-if="item.status === '空闲'">
                                <div class="tw-w-full tw-h-1/3 tw-text-lg tw-text-gray-500">
                                    <span>状态：</span> <span class="tw-text-green-500">可用</span>
                                </div>
                            </div>
                            <div v-else>
                                <div class="tw-w-full tw-h-1/3 tw-text-lg tw-text-gray-500">
                                    <span>状态：</span> <span class="tw-text-red-500">不可用</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <el-dialog v-model="dialogVisible" class="tw-space-y-3" title="充电站信息" width="30%" :before-close="handleClose">
            <div><span>充电站名称：{{ showStation.name }}</span></div>
            <div>
                <span>充电站地址：{{ showStation.address }}</span>
            </div>
            <div>
                <span>充电站瓦数：{{ showStation.power }}</span>
            </div>
            <div>
                <span>预计时间：{{ showStation.predict_time }}min ({{ showStation.predict_duration }} )</span>
            </div>
            <div>
                <span class="tw-mr-3">支付方式</span>
                <el-radio-group class="tw-ml-2" v-model="value">
                    <el-radio label="1">扫码</el-radio>
                    <el-radio label="2">钱包</el-radio>
                </el-radio-group>
            </div>

            <div v-if="value == 1">
                <img class="tw-w-40 tw-h-40"
                    src="https://upload.wikimedia.org/wikipedia/commons/thumb/4/4e/QRcode_image.svg/1200px-QRcode_image.svg.png"
                    alt="">
            </div>

            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">Cancel</el-button>
                    <el-button type="primary" @click="confirm">
                        Confirm
                    </el-button>
                </span>
            </template>
        </el-dialog>


    </div>
</template>
 
<script setup>
import { ref } from 'vue';
import { api } from '../../boot/api';
import { dayjs, ElMessage } from 'element-plus';
let stations = ref([]);
let dialogVisible = ref(false);
let showStation = ref({})
let value = ref('1');
let battery = ref(150);
let info = ref(JSON.parse(sessionStorage.getItem('info')))
api({ url: '/chargingStation/getall', method: 'get' }).then(res => {
    stations.value = res.data.data;
    console.log(stations.value);
    sessionStorage.setItem('stations', JSON.stringify(stations));
})

let handleClicked = (item) => {
    if (item.status !== '空闲') {
        ElMessage.error('该充电站未激活');
        return;
    } else {
        dialogVisible.value = true;
        showStation.value = item;
        showStation.value.predict_time = Math.ceil((battery.value / item.power) * 60);

        showStation.value.predict_duration = dayjs().add(showStation.value.predict_time, 'minute').format('YYYY-MM-DD HH:mm:ss');
    }
}
let confirm = () => {
    dialogVisible.value = false;
    let formData = new FormData();
    formData.append('stationId', showStation.value.id);
    formData.append('status', '使用中');
    api({
        url: '/chargingStation/updatestatus',
        data: formData,
        method: 'post',
    }).then(res => {
        let formData = new FormData();
        formData.append('userId', info.value.userId);
        formData.append('chargerId', showStation.value.id);
        formData.append('chargerName', showStation.value.name);
        formData.append('startTime', dayjs().format('YYYY-MM-DD HH:mm:ss'));
        formData.append('username', info.value.username)
        api({
            url: '/orders/insertorder',
            data: formData,
            method: 'post',
        }).then(r => {
            if (r.data.code === 200) {
                ElMessage.success('充电成功');
                stations.value = res.data.data;
            } else {
                ElMessage.error('充电失败');
            }
        })


    })
}


</script> 
 
<style scoped>
.el-carousel__item h3 {
    display: flex;
    color: #475669;
    opacity: 0.75;
    line-height: 300px;
    margin: 0;
}

.el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
    background-color: #d3dce6;
}

.section {
    margin-top: 35%
}
</style>