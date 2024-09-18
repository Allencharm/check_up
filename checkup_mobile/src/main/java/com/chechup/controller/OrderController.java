package com.chechup.controller;

import com.checkup.bean.Result;
import com.checkup.entity.TOrderEntity;
import com.checkup.service.TOrderService;
import com.checkup.util.MessageConstant;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("order")
public class OrderController {

    @Reference
    private TOrderService orderService;

    //预约下单
    @RequestMapping("submitOrder")
    public Result submitOrder(@RequestBody Map map){
        System.out.println(map);
        Result result = orderService.submitOrder(map);
        return result;
    }

    //下单信息
    @RequestMapping("findOrderById")
    public Result findOrderById(int id){
        try{
            TOrderEntity order = orderService.findOrderById(id);
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS,order);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ORDER_FAIL,null);
        }
    }
}
