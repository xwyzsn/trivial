package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.common.Result;
import com.example.demo.common.Utils;
import com.example.demo.entity.MaintenanceRecords;
import com.example.demo.mapper.MaintenanceRecordsMapper;
import com.example.demo.service.MaintenanceRecordsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xwyzsn
 * @since 2023-03-27
 */
@Service
public class MaintenanceRecordsServiceImpl extends ServiceImpl<MaintenanceRecordsMapper, MaintenanceRecords> implements MaintenanceRecordsService {

    @Autowired
    private MaintenanceRecordsMapper maintenanceRecordsMapper;
    @Autowired
    private Utils utils;
    @Autowired
    private ChargingStationServiceImpl chargingStationService;

    public Result reportError(Integer stationId, String stationName, String description, String reportTime,String address) {
        MaintenanceRecords maintenanceRecords = new MaintenanceRecords();
        maintenanceRecords.setAddress(address);
        maintenanceRecords.setStationId(stationId);
        maintenanceRecords.setStationName(stationName);
        maintenanceRecords.setDescription(description);
        maintenanceRecords.setReportTime(utils.stringToDate(reportTime));
        maintenanceRecordsMapper.insert(maintenanceRecords);
        chargingStationService.updateStationStatus(stationId,"已报修");
        return Result.succ(null);
    }

    public Result getAll() {
        return Result.succ(maintenanceRecordsMapper.selectList(null));
    }

    public Result getNotAvail() {
        QueryWrapper<MaintenanceRecords> maintenanceRecordsQueryWrapper = new QueryWrapper<>();
        maintenanceRecordsQueryWrapper.eq("status","未修复");
        return Result.succ(maintenanceRecordsMapper.selectList(maintenanceRecordsQueryWrapper));
    }

    public Result getHistory(Integer staffId) {
        QueryWrapper<MaintenanceRecords> maintenanceRecordsQueryWrapper = new QueryWrapper<>();
        maintenanceRecordsQueryWrapper.eq("maintenance_staff_id",staffId);
        return Result.succ(maintenanceRecordsMapper.selectList(maintenanceRecordsQueryWrapper));
    }

    public Result finish( String stationName, String address, String description, String finishTime,
                         Integer maintenanceStaffId, String maintenanceStaffName,Integer id,float cost){
        UpdateWrapper<MaintenanceRecords> maintenanceRecordsUpdateWrapper = new UpdateWrapper<>();
        QueryWrapper<MaintenanceRecords> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        MaintenanceRecords maintenanceRecords = maintenanceRecordsMapper.selectOne(queryWrapper);
        chargingStationService.updateStationStatus(maintenanceRecords.getStationId(),"空闲");
        maintenanceRecordsUpdateWrapper.eq("id",id);
        maintenanceRecordsUpdateWrapper.set("station_name",stationName);
        maintenanceRecordsUpdateWrapper.set("address",address);
        maintenanceRecordsUpdateWrapper.set("description",description);
        maintenanceRecordsUpdateWrapper.set("status","已修复");
        maintenanceRecordsUpdateWrapper.set("cost",cost);
        maintenanceRecordsUpdateWrapper.set("finish_time",utils.stringToDate(finishTime));
        maintenanceRecordsUpdateWrapper.set("maintenance_staff_id",maintenanceStaffId);
        maintenanceRecordsUpdateWrapper.set("maintenance_staff_name",maintenanceStaffName);
        maintenanceRecordsMapper.update(null,maintenanceRecordsUpdateWrapper);
        return getNotAvail();
    }
}
