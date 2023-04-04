package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.common.Result;
import com.example.demo.entity.Vehicle;
import com.example.demo.mapper.VehicleMapper;
import com.example.demo.service.VehicleService;
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
public class VehicleServiceImpl extends ServiceImpl<VehicleMapper, Vehicle> implements VehicleService {

    @Autowired
    private VehicleMapper vehicleMapper;
    public Result getCar(Integer userId) {
        QueryWrapper<Vehicle>  vehicleQueryWrapper=new QueryWrapper<>();
        vehicleQueryWrapper.eq("user_id",userId);
        return Result.succ(vehicleMapper.selectList(vehicleQueryWrapper));
    }

    public Result updateCar(Integer id, Integer userId, String brand, String model, float batterySize, float wattage) {
        UpdateWrapper<Vehicle> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        if(userId!=null){
            updateWrapper.set("user_id",userId);
        }
        if (brand != null) {
            updateWrapper.set("brand",brand);
        }
        if (model != null) {
            updateWrapper.set("model",model);
        }
        if (batterySize != 0) {
            updateWrapper.set("battery_size",batterySize);
        }
        if (wattage != 0) {
            updateWrapper.set("wattage",wattage);
        }
        vehicleMapper.update(null,updateWrapper);
        return Result.succ("修改成功");

    }

    public Result deleteCar(Integer id) {
        QueryWrapper<Vehicle> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        vehicleMapper.delete(queryWrapper);
        return Result.succ("删除成功");
    }
}
