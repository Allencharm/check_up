package com.checkup.controller;

import com.checkup.bean.Result;
import com.checkup.service.ReportService;
import com.checkup.util.MessageConstant;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("report")
public class ReportController {

    @Reference
    private ReportService reportService;

    //会员数量统计
    @RequestMapping("getMemberReport")
    public Result getMemberReport(){
        try{
            Map map = reportService.getMemberReport();
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL,null);
        }
    }

    //套餐统计 -- 每种套餐的订单数
    @RequestMapping("getSetmealReport")
    public Result getSetmealReport(){
        try{
            Map map = reportService.getSetmealReport();
            return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL,null);
        }
    }
}
