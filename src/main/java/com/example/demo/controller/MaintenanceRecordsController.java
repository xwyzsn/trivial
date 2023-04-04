package com.example.demo.controller;


import com.example.demo.common.Result;
import com.example.demo.mapper.MaintenanceRecordsMapper;
import com.example.demo.service.impl.MaintenanceRecordsServiceImpl;
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
@RequestMapping("/maintenanceRecords")
@CrossOrigin(origins = "*")
public class MaintenanceRecordsController {

    @Autowired
    private MaintenanceRecordsServiceImpl MaintenanceRecordsServiceImpl;

    @PostMapping(value = "report")
    public Result reportError(@RequestParam(value = "stationId" ) Integer stationId,
                              @RequestParam(value="stationName") String stationName,
                              @RequestParam(value = "address") String address,
                              @RequestParam(value = "description") String description,
                              @RequestParam(value = "reportTime") String reportTime){
        return MaintenanceRecordsServiceImpl.reportError(stationId,stationName,description,reportTime,address);
    }
    @GetMapping(value = "getall")
    public Result getAll(){
        return MaintenanceRecordsServiceImpl.getAll();
    }
}
