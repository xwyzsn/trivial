package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.common.Result;
import com.example.demo.common.Utils;
import com.example.demo.entity.RepairPerson;
import com.example.demo.mapper.RepairPersonMapper;
import com.example.demo.service.RepairPersonService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xwyzsn
 * @since 2023-03-27
 */
@Service
public class RepairPersonServiceImpl extends ServiceImpl<RepairPersonMapper, RepairPerson> implements RepairPersonService {


    @Autowired
    private RepairPersonMapper repairPersonMapper;
    @Autowired
    private Utils utils;

    public Result getAll() {
        return Result.succ(repairPersonMapper.selectList(null));
    }

    public Result delete(Integer id) {
        QueryWrapper<RepairPerson> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        repairPersonMapper.delete(wrapper);
        return Result.succ(null);
    }

    public Result update(Integer id, String name, String phone, String password, String email, String username) {
        Object user =  utils.checkUsernameExist(username);
        if(user != null){
            if(user instanceof RepairPerson){
                if(!Objects.equals(((RepairPerson) user).getId(), id)){
                    return Result.fail("用户名已存在");
                }
            }else{
                return Result.fail("用户名存在");
            }
        }
        UpdateWrapper<RepairPerson> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id);
        wrapper.set("name", name);
        wrapper.set("phone", phone);
        wrapper.set("password",password);
        wrapper.set("email", email);
        wrapper.set("username", username);

        repairPersonMapper.update(null, wrapper);
        return Result.succ(null);
    }

    public Result add( String name, String phone, String password, String email, String username) {
        Object user =  utils.checkUsernameExist(username);
        if(user != null){
            return Result.fail("用户名已存在");
        }
        RepairPerson repairPerson = new RepairPerson();
        repairPerson.setName(name);
        repairPerson.setPhone(phone);
        repairPerson.setPassword(password);
        repairPerson.setEmail(email);
        repairPerson.setUsername(username);
        repairPersonMapper.insert(repairPerson);
        return Result.succ(null);
    }
}
