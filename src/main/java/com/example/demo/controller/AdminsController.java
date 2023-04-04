package com.example.demo.controller;


import com.example.demo.common.Result;
import com.example.demo.service.impl.AdminsServiceImpl;
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
@RequestMapping("/admins")
@CrossOrigin(origins = "*")
public class AdminsController {
    @Autowired
    private AdminsServiceImpl adminsService;
    @PostMapping(value = "update")
    public Result update(@RequestParam("id") Integer id,
                         @RequestParam("username") String username,
                            @RequestParam("password") String password,
                            @RequestParam("email") String email,
                            @RequestParam("phone") String phone,
                            @RequestParam("name") String name){
        return adminsService.updateAdmin(id,username,password,email,phone,name);
    }
}
