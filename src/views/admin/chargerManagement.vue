<template>
    <div class="tw-w-full tw-h-full">
        <MyTitle content="充电桩管理" />
        <el-button type="primary" size="lg" @click="addNewModal = true">新增</el-button>
        <el-table v-if="data" :data="data" class="tw-w-full tw-h-full">
            <el-table-column prop="name" label="名称">
            </el-table-column>
            <el-table-column prop="address" label="地址"></el-table-column>
            <el-table-column prop="status" label="状态"></el-table-column>
            <el-table-column prop="power" label="瓦数"></el-table-column>
            <el-table-column fixed="right" label="Operations" width="200">
                <template #default="scope">
                    <el-button link type="primary" size="small" @click="handleClick(scope.$index, scope.row)">编辑</el-button>
                    <el-button link type="primary" size="small"
                        @click="confirmDelete(scope.$index, scope.row)">删除</el-button>
                    <el-button link type="primary" size="small"
                        @click="handleOpenReport(scope.$index, scope.row)">报修</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-dialog v-model="addNewModal" width="30%" title="充电桩添加">
            <el-form label-width="100px">
                <el-form-item label="名称">
                    <el-input v-model="newData.name" placeholder="名称"></el-input>
                </el-form-item>
                <el-form-item label="地址">
                    <el-input v-model="newData.address" placeholder="地址"></el-input>
                </el-form-item>
                <el-form-item label="状态">
                    <el-select v-model="newData.status" class="m-2" placeholder="请选择">
                        <el-option key="1" label="空闲" value="空闲" />
                        <el-option key="2" label="使用中" value="使用中" />
                        <el-option key="3" label="维修中" value="维修中" />

                    </el-select>
                </el-form-item>
                <el-form-item label="瓦数">
                    <el-input v-model="newData.power" placeholder="瓦数"></el-input>
                </el-form-item>

            </el-form>
            <template #footer>
                <el-button @click="addNewModal = false">取 消</el-button>
                <el-button type="primary" @click="confirmAdd">确 定</el-button>
            </template>
        </el-dialog>
        <el-dialog v-model="showModal" title="充电桩修改">
            <el-form label-width="100px">
                <el-form-item label="名称">
                    <el-input v-model="showData.name" placeholder="名称"></el-input>
                </el-form-item>
                <el-form-item label="地址">
                    <el-input v-model="showData.address" placeholder="地址"></el-input>
                </el-form-item>
                <el-form-item label="状态">
                    <el-select v-model="showData.status" class="m-2" placeholder="请选择">
                        <el-option key="1" label="空闲" value="空闲" />
                        <el-option key="2" label="使用中" value="使用中" />
                        <!-- <el-option key="3" label="维修中" value="维修中" /> -->

                    </el-select>

                </el-form-item>
                <el-form-item label="瓦数">
                    <el-input v-model="showData.power" placeholder="瓦数"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <el-button @click="showModal = false">取 消</el-button>
                <el-button type="primary" @click="confirmUpdate">确 定</el-button>
            </template>
        </el-dialog>
        <el-dialog v-model="reportDataModal">
            <el-form label-width="150px">
                <el-form-item label="充电桩名称">
                    <el-input v-model="reportData.stationName" disabled placeholder="充电桩名称"></el-input>
                </el-form-item>
                <el-form-item label="充电桩地址">
                    <el-input v-model="reportData.address" disabled placeholder="充电桩地址"></el-input>
                </el-form-item>
                <el-form-item label="故障描述">
                    <el-input v-model="reportData.description" placeholder="故障描述"></el-input>
                </el-form-item>

            </el-form>
            <template #footer>
                <el-button @click="reportDataModal = false">取 消</el-button>
                <el-button type="primary" @click="confirmReport(reportData)">确 定</el-button>
            </template>
        </el-dialog>

    </div>
</template>
 
<script setup>
import { ref } from 'vue'
import { api } from '../../boot/api';
import MyTitle from '../../components/MyTitle.vue'
import { dayjs, ElMessage } from 'element-plus';
let data = ref([])
let reportData = ref({
    stationId: '',
    stationName: '',
    description: '',
    address: '',
    reportTime: ''
})
let reportDataModal = ref(false)
let showData = ref({})
let showModal = ref(false)
let newData = ref({
    name: '',
    address: '',
    status: '',
    power: ''
})
let addNewModal = ref(false)
api({
    url: '/chargingStation/getall',
    method: 'get'
}).then(res => {
    data.value = res.data.data
    data.value = data.value.filter(item => {
        return item.status === '空闲' || item.status === '使用中'
    })
})
let handleClick = (index, row) => {
    showData.value = row
    showModal.value = true
}
let handleOpenReport = (index, row) => {
    reportData.value.stationId = row.id
    reportData.value.stationName = row.name
    reportData.value.address = row.address
    reportDataModal.value = true
}
let confirmReport = (row) => {
    console.log(row)
    let formData = new FormData()
    formData.append('stationId', row.stationId)
    formData.append('stationName', row.stationName)
    formData.append('description', row.description)
    formData.append('address', row.address)
    formData.append('reportTime', dayjs().format('YYYY-MM-DD HH:mm:ss'))
    api({
        url: '/maintenanceRecords/report',
        method: 'post',
        data: formData
    }).then(res => {
        if (res.data.code === 200) {
            ElMessage({
                type: 'success',
                message: '上报成功'
            });
            api({
                url: '/chargingStation/getall',
                method: 'get'
            }).then(res => {
                data.value = res.data.data
                data.value = data.value.filter(item => {
                    return item.status === '空闲' || item.status === '使用中'
                })
            })
        } else {
            ElMessage({
                type: 'error',
                message: '上报失败'
            });
        }
    }).then(() => {
        reportDataModal.value = false

    })
}
let confirmAdd = () => {
    let formData = new FormData()
    formData.append('name', newData.value.name)
    formData.append('address', newData.value.address)
    formData.append('status', newData.value.status)
    formData.append('power', newData.value.power)

    api({
        url: '/chargingStation/add',
        method: 'post',
        data: formData
    }).then(res => {
        let response = res.data
        if (response.code == 200) {
            addNewModal.value = false
            ElMessage({
                type: 'success',
                message: '添加成功'
            });
            data.value = response.data
        } else {
            ElMessage({
                type: 'error',
                message: '添加失败'
            });
        }

    })
}
let confirmDelete = (index, row) => {
    api({
        url: '/chargingStation/delete?id=' + row.id,
        method: 'delete'
    }).then(res => {
        if (res.data.code == 200) {
            ElMessage({
                type: 'success',
                message: '删除成功'
            });
            data.value.splice(index, 1)
        } else {
            ElMessage({
                type: 'error',
                message: '删除失败'
            });
        }
    })
}
let confirmUpdate = () => {
    let formData = new FormData()
    formData.append('id', showData.value.id)
    formData.append('name', showData.value.name)
    formData.append('address', showData.value.address)
    formData.append('status', showData.value.status)
    formData.append('power', showData.value.power)

    api({
        url: '/chargingStation/update',
        method: 'post',
        data: formData
    }).then(res => {
        let response = res.data
        if (response.code == 200) {
            showModal.value = false
            ElMessage({
                type: 'success',
                message: '修改成功'
            });
        } else {
            ElMessage({
                type: 'error',
                message: '修改失败'
            });
        }

    })
}

</script> 
 
<style scoped></style>