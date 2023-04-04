package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.common.Result;
import com.example.demo.entity.ChargingStation;
import com.example.demo.entity.Orders;
import com.example.demo.mapper.ChargingStationMapper;
import com.example.demo.service.ChargingStationService;
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
public class ChargingStationServiceImpl extends ServiceImpl<ChargingStationMapper, ChargingStation> implements ChargingStationService {

    @Autowired
    private ChargingStationMapper stationMapper;

    public Result getAllStationInfo() {

        return Result.succ(stationMapper.selectList(null));
    }

    public Result updateStationStatus(Integer stationId, String status) {
        UpdateWrapper<ChargingStation> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", stationId);
        wrapper.set("status", status);
        stationMapper.update(null, wrapper);
        Orders order = new Orders();

        return Result.succ(stationMapper.selectList(null));
    }

    public Result deleteStation(String stationId) {
        UpdateWrapper<ChargingStation> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", stationId);
        stationMapper.delete(updateWrapper);
        return Result.succ(stationMapper.selectList(null));
    }

    public Result addStation(String name, String address, String status, Float power) {
        ChargingStation station = new ChargingStation();
        station.setName(name);
        station.setAddress(address);
        station.setStatus(status);
        station.setPower(power);
        stationMapper.insert(station);
        return Result.succ(stationMapper.selectList(null));
    }

    public Result updateStation(Integer id, String name, String address, String status, Float power) {
        UpdateWrapper<ChargingStation> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        updateWrapper.set("name", name);
        updateWrapper.set("address", address);
        updateWrapper.set("status", status);
        updateWrapper.set("power", power);
        stationMapper.update(null, updateWrapper);
        return Result.succ(stationMapper.selectList(null));
    }

}
