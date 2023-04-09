package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.service.impl.WalletServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
@CrossOrigin(origins="*")
public class WalletController {
    @Autowired
    private WalletServiceImpl walletService;

    @GetMapping("get")
    public Result getUser(@RequestParam("userId") Integer userId){
        return walletService.getUser(userId);
    }

    @PostMapping("update")
    public Result updateUser(@RequestParam("userId") Integer userId,
                             @RequestParam("method") String method,
                             @RequestParam("num") Float num){
        return walletService.updateUser(userId,method,num);
    }

}
