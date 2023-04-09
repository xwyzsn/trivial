<template>
    <div class="tw-w-full tw-h-full">
        <MyTitle content="车辆信息" />
        <el-table v-if="cars.length" :data="cars">
            <el-table-column prop="brand" label="品牌"></el-table-column>
            <el-table-column prop="model" label="外观"></el-table-column>
            <el-table-column prop="batterySize" label="电池大小"></el-table-column>
            <el-table-column prop="wattage" label="瓦数"></el-table-column>
            <el-table-column prop="createdAt" label="创建时间"></el-table-column>
            <el-table-column prop="updatedAt" label="更新时间"></el-table-column>
            <el-table-column fixed="right" label="操作" width="120">
                <template #default="scope">
                    <el-button link type="error" size="small"
                        @click.prevent="deleteCar(scope.$index, scope.row)">解绑</el-button>
                    <el-button link type="primary" size="small" @click="handleClick(scope.$index, scope.row)">编辑</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div v-else>
            <el-button type="primary" @click="showModal = true">绑定车辆信息</el-button>
        </div>
        <el-dialog v-model="dialogVisible" title="信息编辑" width="60%" :before-close="handleClose">
            <div>
                <el-form label-width="100px">
                    <el-form-item label="品牌">
                        <el-input v-model="showCar.brand" placeholder="品牌"></el-input>
                    </el-form-item>
                    <el-form-item label="外观">
                        <el-input v-model="showCar.model" placeholder="外观"></el-input>
                    </el-form-item>
                    <el-form-item label="电池大小">
                        <el-input v-model="showCar.batterySize" placeholder="电池大小"></el-input>
                    </el-form-item>
                    <el-form-item label="瓦数">
                        <el-input v-model="showCar.wattage" placeholder="瓦数"></el-input>
                    </el-form-item>
                </el-form>
            </div>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">Cancel</el-button>
                    <el-button type="primary" @click=" updateVehicle">
                        Confirm
                    </el-button>
                </span>
            </template>
        </el-dialog>
        <el-dialog v-model="showModal" :close-on-press-escape="false" :show-close="false" :close-on-click-modal="false">
            <template #title>
                <span>绑定车辆信息</span>
            </template>
            <el-form label-width="100px" >
                <el-form-item label="品牌" >
                    <el-input v-model="showData.brand"></el-input>
                </el-form-item>
                <el-form-item label="型号">
                    <el-input v-model="showData.model"></el-input>
                </el-form-item>
                <el-form-item label="电池大小">
                    <el-input-number  v-model="showData.batterySize"></el-input-number>
                </el-form-item>
                <el-form-item label="充电瓦数">
                    <el-input-number v-model="showData.chargingPower"></el-input-number>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="bindCar">确 定</el-button>
            </span>

        </el-dialog>
    </div>
</template>
 
<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus';
import { api } from '../../boot/api';
import MyTitle from '../../components/MyTitle.vue';
let info = ref(JSON.parse(sessionStorage.getItem('info')))
let cars = ref([])
let showCar = ref({})
let dialogVisible = ref(false)
let showModal = ref(false)
let showData = ref({
    brand: '',
    model: '',
    batterySize: '',
    chargingPower: ''
})
let bindCar = () =>{
    let formData = new FormData()
    formData.append('brand', showData.value.brand)
    formData.append('model', showData.value.model)
    formData.append('batterySize', showData.value.batterySize)
    formData.append('wattage', showData.value.chargingPower)
    formData.append('userId', info.value.userId)
    api({
        url: '/vehicle/addcar',
        method: 'POST',
        data: formData
    }).then(res => {
        let response = res.data
        if (response.code === 200) {
            ElMessage.success('绑定成功')
            showModal.value = false
            api({ url: '/vehicle/getcar?user_id=' + info.value.userId, method: 'GET' }).then(res => {
                let response = res.data
                if (response.code === 200) {
                    cars.value = response.data
                }
            })
        }
    })
}
api({ url: '/vehicle/getcar?user_id=' + info.value.userId, method: 'GET' }).then(res => {
    let response = res.data
    if (response.code === 200) {
        cars.value = response.data
    }
})
let handleClick = (index, row) => {
    showCar.value = row
    dialogVisible.value = true
}
let updateVehicle = () => {
    let formData = new FormData()
    console.log(showCar.value)
    formData.append('id', showCar.value.id)
    formData.append('brand', showCar.value.brand)
    formData.append('model', showCar.value.model)
    formData.append('batterySize', showCar.value.batterySize)
    formData.append('wattage', showCar.value.wattage)

    api({
        url: '/vehicle/updatecar',
        method: 'POST',
        data: formData
    }).then(res => {
        let response = res.data
        if (response.code === 200) {
            ElMessage.success('修改成功')
            dialogVisible.value = false
            api({ url: '/vehicle/getcar?user_id=' + info.value.userId, method: 'GET' }).then(res => {
                let response = res.data
                if (response.code === 200) {
                    cars.value = response.data
                }
            })
        }
    })
}
let deleteCar = (index, row) => {
    api({
        url: '/vehicle/deletecar?id=' + row.id,
        method: 'get',
    }).then(res => {
        let response = res.data
        if (response.code === 200) {
            ElMessage.success('删除成功')
            api({ url: '/vehicle/getcar?user_id=' + info.value.userId, method: 'GET' }).then(res => {
                let response = res.data
                if (response.code === 200) {
                    cars.value = response.data
                }
            })
        }else{
            ElMessage.error('删除失败')
        }
    })
}

</script> 
 
<style scoped></style>