package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Wallet {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private Float money;

    private Date updatedTime;


}
