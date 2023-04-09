package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.Result;
import com.example.demo.entity.Wallet;
import com.example.demo.mapper.WalletMapper;
import com.example.demo.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class WalletServiceImpl extends ServiceImpl<WalletMapper, Wallet> implements WalletService {


    @Autowired
    private WalletMapper walletMapper;


    public Result getUser(Integer userId) {
        QueryWrapper<Wallet> walletQueryWrapper = new QueryWrapper<>();
        walletQueryWrapper.eq("user_id",userId);
        return Result.succ(walletMapper.selectOne(walletQueryWrapper));
    }
    public Result updateUser(Integer userId,String method,Float num){
        QueryWrapper<Wallet> walletQueryWrapper = new QueryWrapper<>();
        walletQueryWrapper.eq("user_id",userId);
        Wallet wallet = walletMapper.selectOne(walletQueryWrapper);
        if(method.equals("add")){
            wallet.setMoney(wallet.getMoney() + num );
        }else if(method.equals("sub")){
            if(wallet.getMoney() < num){
                return Result.fail("余额不足");
            }
            wallet.setMoney(wallet.getMoney()-num);
        }
        walletMapper.updateById(wallet);
        return Result.succ(wallet);
    }
}
