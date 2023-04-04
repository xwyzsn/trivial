package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.common.Result;
import com.example.demo.common.Utils;
import com.example.demo.entity.Orders;
import com.example.demo.mapper.OrdersMapper;
import com.example.demo.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xwyzsn
 * @since 2023-03-27
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private Utils utils;
    public Result getOrder(Integer userId) {
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        return Result.succ(ordersMapper.selectList(queryWrapper));
    }


    public Result updateOrder(Integer userId, String username, Integer chargerId, String chargerName, String startTime, String endTime, Integer duration, Float cost) {
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        Orders orders = ordersMapper.selectOne(queryWrapper);
        orders.setUserId(userId);
        orders.setUsername(username);
        orders.setChargerId(chargerId);
        orders.setChargerName(chargerName);
        Date start = utils.stringToDate(startTime);
        orders.setStartTime(start);
        Date end = utils.stringToDate(endTime);
        orders.setEndTime(end);
        orders.setDurationMinutes(duration);
        orders.setCost(BigDecimal.valueOf(cost));
        ordersMapper.updateById(orders);
        return Result.succ(orders);
    }

    public Result insertOrder(Integer userId, String username, Integer chargerId, String chargerName, String startTime) {
        Orders orders = new Orders();
        orders.setUserId(userId);
        orders.setUsername(username);
        orders.setChargerId(chargerId);
        orders.setChargerName(chargerName);
        Date start = utils.stringToDate(startTime);
        orders.setStartTime(start);
        ordersMapper.insert(orders);
        return Result.succ(orders);
    }



    public Result searchOrder(Integer orderId, Integer chargerId, String startTime, String endTime, Integer type) {
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        if(orderId != null){
            queryWrapper.eq("order_id",orderId);
        }
        if(chargerId != null){
            queryWrapper.eq("charger_id",chargerId);
        }
        if(startTime != null){
            Date start = utils.stringToDate(startTime);
            queryWrapper.ge("start_time",start);
        }
        if(endTime != null){
            Date end = utils.stringToDate(endTime);
            queryWrapper.or(i->i.le("end_time",end).isNull("end_time"));
        }
        if(type == 1){
           List<Orders> orders =  ordersMapper.selectList(queryWrapper).stream().filter(item-> item.getEndTime()!=null).collect(Collectors.toList());;
           return Result.succ(orders);
        }
        if (type == 2) {
            List<Orders> orders = ordersMapper.selectList(queryWrapper).stream().filter(item-> item.getEndTime()==null).toList();
            return Result.succ(orders);
        }
        return Result.succ(ordersMapper.selectList(queryWrapper));
    }
}
