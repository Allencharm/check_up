package com.chechup.controller;

import com.checkup.bean.Result;
import com.checkup.entity.TSetmealEntity;
import com.checkup.pojo.TSetmeal;
import com.checkup.service.TSetmealService;
import com.checkup.util.MessageConstant;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("setmealcli")
public class SetmealController {

    @Reference
    private TSetmealService setmealService;

    //套餐列表
    @RequestMapping("getAllSetmeal")
    public Result getAllSetmeal(){
        try{
            List<TSetmeal> list = setmealService.list();
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_SUCCESS,null);
        }
    }

    //套餐详情
    @RequestMapping("findInfoById")
    public Result findInfoById(int id){
        try{
            TSetmealEntity setmeal = setmealService.findInfoById(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL,null);
        }
    }
}
