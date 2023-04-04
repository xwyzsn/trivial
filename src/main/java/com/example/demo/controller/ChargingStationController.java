package com.example.demo.controller;


import com.example.demo.common.Result;
import com.example.demo.service.impl.ChargingStationServiceImpl;
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
@RequestMapping("/chargingStation")
@CrossOrigin(origins="*")
public class ChargingStationController {

    @Autowired
    private ChargingStationServiceImpl stationService;

    @GetMapping("getall")
    public Result  getAllStationInfo(){
        return stationService.getAllStationInfo();
    }

    @PostMapping("updatestatus")
    public Result updateStationStatus(@RequestParam("stationId") Integer stationId,@RequestParam("status") String status
                                      ){
        return stationService.updateStationStatus(stationId,status);
    }
    @DeleteMapping("delete")
    public Result deleteStation(@RequestParam("id") String stationId){
        return stationService.deleteStation(stationId);
    }
    @PostMapping("add")
    public Result addStation(@RequestParam("name") String name,@RequestParam("address") String address,
                             @RequestParam("status") String status,@RequestParam("power") Float type){
        return stationService.addStation(name,address,status,type);
    }
    @PostMapping("update")
    public Result updateStation(@RequestParam("id") Integer id,@RequestParam("name") String name,@RequestParam("address") String address,
                             @RequestParam("status") String status,@RequestParam("power") Float type){
        return stationService.updateStation(id,name,address,status,type);
    }




}
