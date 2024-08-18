package com.checkup.controller;

import com.checkup.bean.Result;
import com.checkup.service.TOrdersettingService;
import com.checkup.util.MessageConstant;
import com.checkup.util.POIUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yjq
 * @since 2021-10-28
 */
@RestController
@RequestMapping("ordersetting")
public class TOrdersettingController {

    @Value("${setmeal_img_path}")
    private String path;

    @Reference
    private TOrdersettingService ordersettingService;

    //预约条件设置上传模板功能
    @RequestMapping("upload")
    public Result upload(MultipartFile excelFile){
        try{
            if(excelFile != null && !excelFile.isEmpty()){
                List<String[]> list = POIUtils.readExcel(excelFile);
                /*for(String[] strs : list){
                    System.out.println(strs[0]+"--"+strs[1]);
                }*/

                ordersettingService.importExcelToMysql(list);
                return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS,null);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL,null);
    }

    //页面根据当前年月显示日历
    @RequestMapping("findByYearAndMonth")
    public Result findByYearAndMonth(String year,String month){
        try{
            List<Map> list = ordersettingService.findByYearAndMonth(year,month);
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL,null);
        }
    }

    //设置修改可预约数量
    @RequestMapping("edit")
    public Result edit(String date,int num){
        try{
            ordersettingService.update(date,num);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS,null);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL,null);
        }
    }
}

