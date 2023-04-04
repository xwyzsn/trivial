package com.example.demo.controller;


import com.example.demo.common.Result;
import com.example.demo.common.Utils;
import com.example.demo.entity.Admins;
import com.example.demo.entity.RepairPerson;
import com.example.demo.entity.User;
import com.example.demo.service.impl.UserServiceImpl;
import com.google.protobuf.DescriptorProtos;
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
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private Utils utils;
    @GetMapping("getall")
    public Result getAllUser(){
        return userService.getAllUser();
    }

    @PostMapping("login") // @RequestParam only allow form data if want to json use RequestBody
    public Result validLoginUser(@RequestParam(value ="username") String username,@RequestParam(value="password") String password){
        Object person = utils.checkUsernameExist(username);
        if (person == null) {
            return Result.fail("error password or username");
        }
        if(person instanceof User){
            User user = (User) person;
            String pwd = user.getPassword();
            if(pwd.equals(password)){
                return Result.succLogin("user",user);
            }
            else{
                return Result.fail("error");
            }
        } else if (person instanceof Admins) {
            Admins admins = (Admins) person;
            String pwd = admins.getPassword();
            if(pwd.equals(password)){
                return Result.succLogin("admin",admins);
            }
            else{
                return Result.fail("error");
            }
        } else if( person instanceof RepairPerson){
            RepairPerson repairPerson = (RepairPerson) person;
            String pwd = repairPerson.getPassword();
            if(pwd.equals(password)){
                return Result.succLogin("repair",repairPerson);
            }
            else{
                return Result.fail("error");
            }
        }
        return Result.fail("unexpected error");
    }

    @PostMapping("update")
    public Result updateUser(@RequestParam(value = "username") String username,
                             @RequestParam(value = "nickname",required = false) String nickname,
                             @RequestParam(value = "phone",required = false) String phone,
                             @RequestParam(value = "email",required = false) String email,
                             @RequestParam(value = "password",required = false) String password,
                             @RequestParam(value = "userId",required = false) Integer userId ){
        return userService.updateUser(username,nickname,phone,email,password,userId);
    }
    @GetMapping("checkuser")
    public Result checkUser(@RequestParam(value = "username") String username){
        Object person = utils.checkUsernameExist(username);
        if (person != null) {
            return Result.succ(person);
        }
        return Result.fail("no user founded");
    }
    @DeleteMapping("/delete")
    public Result deleteUser(@RequestParam(value = "userId") Integer userId){
        return userService.deleteUser(userId);
    }
    @PostMapping("add")
    public Result addUser(@RequestParam(value = "username") String username,
                          @RequestParam(value = "nickname",required = false) String nickname,
                          @RequestParam(value = "phone",required = false) String phone,
                          @RequestParam(value = "email",required = false) String email,
                          @RequestParam(value = "password",required = false) String password){
        return userService.addUser(username,nickname,phone,email,password);
    }
}
