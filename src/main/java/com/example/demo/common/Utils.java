package com.example.demo.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.Admins;
import com.example.demo.entity.RepairPerson;
import com.example.demo.entity.User;
import com.example.demo.mapper.AdminsMapper;
import com.example.demo.mapper.RepairPersonMapper;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Utils {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AdminsMapper adminsMapper;
    @Autowired
    private RepairPersonMapper repairPersonMapper;

    public Object checkUsernameExist(String username){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            return user;
        }
        QueryWrapper<Admins> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("username",username);
        Admins admins = adminsMapper.selectOne(queryWrapper1);
        if (admins != null) {
            return admins;
        }
        QueryWrapper<RepairPerson> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("username",username);
        RepairPerson repairPerson = repairPersonMapper.selectOne(queryWrapper2);
        return repairPerson;
    }

    public Date stringToDate(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date date1 = sdf.parse(date);
            return date1;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
