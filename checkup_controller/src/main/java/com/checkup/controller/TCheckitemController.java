package com.checkup.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.checkup.bean.QueryPageBean;
import com.checkup.bean.Result;
import com.checkup.pojo.TCheckitem;
import com.checkup.service.TCheckitemService;
import com.checkup.util.MessageConstant;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yjq
 * @since 2021-10-28
 */
@RestController
@RequestMapping("/checkitem")
public class TCheckitemController {

    @Reference
    private TCheckitemService tCheckitemService;

    //分页列表查询
    @RequestMapping("findPage")
    public Result findPage(@RequestBody QueryPageBean pageBean){
        try {
            IPage<TCheckitem> page = tCheckitemService.findPage(pageBean);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, page);
        } catch (Exception e){
            return new Result(false, MessageConstant.QUERY_CHECKITEM_SUCCESS, null);
        }
    }

    //添加
    @RequestMapping("add")
    public Result add(@RequestBody TCheckitem tCheckitem){
        try {
            tCheckitemService.save(tCheckitem);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS, null);
        } catch (Exception e){
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL, null);
        }
    }

    //回显
    @RequestMapping("findOne")
    public Result findOne(@RequestParam("itemId") int itemId){
        try {
            TCheckitem tCheckitem = tCheckitemService.getById(itemId);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, tCheckitem);
        } catch (Exception e){
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL, null);
        }
    }

    //修改
    @RequestMapping("edit")
    public Result edit(@RequestBody TCheckitem tCheckitem){
        try {
            boolean updateById = tCheckitemService.updateById(tCheckitem);
            return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS, tCheckitem);
        } catch (Exception e){
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL, null);
        }
    }

    //删除
    @RequestMapping("deleteOne")
    public Result deleteOne(@RequestParam("itemId") int itemId){
        try {
            boolean removeById = tCheckitemService.removeById(itemId);
            return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS, null);
        } catch (Exception e){
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL, null);
        }
    }

    //编辑检查组检查项列表
    @RequestMapping("list")
    public Result list(){
        try {
            List<TCheckitem> list = tCheckitemService.list();
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, list);
        } catch (Exception e){
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL, null);
        }
    }
}

