package com.example.demo.service.impl;

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

}
