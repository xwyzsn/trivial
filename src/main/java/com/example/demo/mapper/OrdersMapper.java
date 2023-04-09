package com.example.demo.mapper;

import com.example.demo.entity.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xwyzsn
 * @since 2023-03-27
 */
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

}