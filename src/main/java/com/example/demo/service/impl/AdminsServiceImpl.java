package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.common.Result;
import com.example.demo.common.Utils;
import com.example.demo.entity.Admins;
import com.example.demo.mapper.AdminsMapper;
import com.example.demo.service.AdminsService;
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
public class AdminsServiceImpl extends ServiceImpl<AdminsMapper, Admins> implements AdminsService {

    @Autowired
    private AdminsMapper adminsMapper;
    @Autowired
    private Utils utils;
    public Result updateAdmin(Integer id, String username, String password, String email, String phone, String name) {
        UpdateWrapper<Admins> updateWrapper = new UpdateWrapper<>();
        Object user = utils.checkUsernameExist(username);
        if(user != null ) {
            if (user instanceof Admins admins) {
                if (!Objects.equals(admins.getId(), id)) {
                    return Result.fail("用户名已存在");
                }
            }
            else{
                return Result.fail("用户名已存在");
            }
        }
        updateWrapper.eq("id",id);
        updateWrapper.set("username",username);
        updateWrapper.set("password",password);
        updateWrapper.set("email",email);
        updateWrapper.set("phone",phone);
        updateWrapper.set("name",name);
        adminsMapper.update(null,updateWrapper);
        return Result.succ(null);
    }
}
