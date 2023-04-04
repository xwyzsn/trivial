package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xwyzsn
 * @since 2023-03-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    public Result getAllUser() {

        List<User> userList=userMapper.selectList(null);
        return Result.succ(userList);

    }

    public Result updateUser(String username, String nickname, String phone, String email, String password,Integer userId) {
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();

        if(userId != null){
            userUpdateWrapper.eq("user_id",userId);
            userUpdateWrapper.set("username",username);
        }
        else{
            userUpdateWrapper.eq("username",username);
        }
        if (nickname != null) {
            userUpdateWrapper.set("nickname",nickname);
        }
        if (phone != null) {
            userUpdateWrapper.set("phone",phone);
        }
        if (email != null) {
            userUpdateWrapper.set("email",email);
        }
        if (password != null) {
            userUpdateWrapper.set("password",password);
        }
        userMapper.update(null,userUpdateWrapper);
        return Result.succ("update success");
    }

    public Result deleteUser(Integer userId) {
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("user_id",userId);
        userMapper.delete(userUpdateWrapper);
        return Result.succ("delete success");
    }

    public Result addUser(String username, String nickname, String phone, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setNickname(nickname);
        user.setPhone(phone);
        user.setEmail(email);
        user.setPassword(password);
        user.setWalletBalance(BigDecimal.ZERO);
        userMapper.insert(user);
        return Result.succ("add success");
    }

}
