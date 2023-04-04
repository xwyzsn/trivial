package com.example.demo.controller;


import com.example.demo.common.Result;
import com.example.demo.service.impl.VehicleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xwyzsn
 * @since 2023-03-27
 */
@RestController
@RequestMapping("/vehicle")
@CrossOrigin(origins = "*")
public class VehicleController {

    @Autowired
    private VehicleServiceImpl vehicleService;

    @GetMapping("getcar")
    public Result getCar(@RequestParam("user_id") Integer userId){
        return vehicleService.getCar(userId);
    }
    @PostMapping("updatecar")
    public Result updateCar(@RequestParam("id" ) Integer id,
                            @RequestParam(value = "userId" ,required = false) Integer userId,
                            @RequestParam(value = "brand",required = false) String brand,
                            @RequestParam(value = "model",required = false) String model,
                            @RequestParam(value = "batterySize",required = false) float batterySize,
                            @RequestParam(value = "wattage",required = false) float wattage){
        return vehicleService.updateCar(id,userId,brand,model,batterySize,wattage);

    }
    @GetMapping("deletecar")
    public Result deleteCar(@RequestParam("id") Integer id){
        return vehicleService.deleteCar(id);
    }
}
