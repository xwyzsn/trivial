package com.example.demo.controller;


import com.example.demo.common.Result;
import com.example.demo.service.impl.OrdersServiceImpl;
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
@RequestMapping("/orders")
@CrossOrigin(origins = "*")
public class OrdersController {

    @Autowired
    private OrdersServiceImpl ordersService;

    @GetMapping("getorder")
    public Result getOrder(@RequestParam(value = "user_id") Integer  userId){
        return ordersService.getOrder(userId);
    }

    @PostMapping("insertorder")
    public Result insertOrder(@RequestParam(value = "userId") Integer userId,
                              @RequestParam(value = "username") String username,
                              @RequestParam(value = "chargerId") Integer chargerId,
                              @RequestParam(value = "chargerName") String chargerName,
                              @RequestParam(value = "startTime") String startTime
                              ){
        return ordersService.insertOrder(userId,username,chargerId,chargerName,startTime);
    }
    @PostMapping("updateorder")
    public Result updateOrder(@RequestParam(value = "userId") Integer userId,
                              @RequestParam(value = "username") String username,
                              @RequestParam(value = "chargerId") Integer chargerId,
                              @RequestParam(value = "chargerName") String chargerName,
                              @RequestParam(value = "startTime") String startTime,
                              @RequestParam(value = "endTime") String endTime,
                              @RequestParam(value = "duration") Integer duration,
                              @RequestParam(value = "cost") Float cost){
        return ordersService.updateOrder(userId,username,chargerId,chargerName,startTime,endTime,duration,cost);
    }
    @PostMapping("finishorder")
    public Result finishOrder(@RequestParam(value = "userId") Integer userId,
                              @RequestParam(value = "orderId") Integer orderId,
                              @RequestParam(value = "username") String username,
                              @RequestParam(value = "chargerId") Integer chargerId,
                              @RequestParam(value = "chargerName") String chargerName,
                              @RequestParam(value = "startTime") String startTime,
                              @RequestParam(value = "endTime") String endTime,
                              @RequestParam(value = "duration") Integer duration,
                              @RequestParam(value = "cost") Float cost){
        return ordersService.finishOrder(userId,username,chargerId,chargerName,startTime,endTime,duration,cost,orderId);
    }
    @GetMapping("searchorder")
    public Result searchOrder(@RequestParam(value = "orderId",required = false) Integer orderId,
                              @RequestParam(value = "chargerId",required = false) Integer chargerId,
                              @RequestParam(value = "startTime",required = false) String startTime,
                              @RequestParam(value = "endTime",required = false) String endTime,
                              @RequestParam(value = "type") Integer type){
        return ordersService.searchOrder(orderId,chargerId,startTime,endTime,type);
    }
}
