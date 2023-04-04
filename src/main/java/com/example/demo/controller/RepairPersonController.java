package com.example.demo.controller;


import com.example.demo.common.Result;
import com.example.demo.service.impl.RepairPersonServiceImpl;
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
@RequestMapping("/repairPerson")
@CrossOrigin(origins = "*")
public class RepairPersonController {

    @Autowired
    private RepairPersonServiceImpl repairPersonService;

    @GetMapping("getall")
    public Result getAll(){
        return repairPersonService.getAll();
    }
    @DeleteMapping("delete")
    public Result delete(@RequestParam("id") Integer id){
        return repairPersonService.delete(id);
    }
    @PostMapping("update")
    public Result update(@RequestParam("id") Integer id,
                         @RequestParam(value = "name",required = false) String name,
                         @RequestParam(value = "phone",required = false) String phone,
                         @RequestParam(value = "password",required = false) String password,
                         @RequestParam(value = "email",required = false) String email,
                         @RequestParam(value = "username",required = false) String username){
        return repairPersonService.update(id,name,phone,password,email,username);
    }

    @PostMapping("add")
    public Result add(@RequestParam(value = "name",required = false) String name,
                        @RequestParam(value = "phone",required = false) String phone,
                        @RequestParam(value = "password",required = false) String password,
                        @RequestParam(value = "email",required = false) String email,
                        @RequestParam("username") String username){
        return repairPersonService.add(name,phone,password,email,username);
    }

}
