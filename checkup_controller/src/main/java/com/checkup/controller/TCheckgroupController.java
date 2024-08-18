package com.checkup.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.checkup.bean.QueryPageBean;
import com.checkup.bean.Result;
import com.checkup.entity.TCheckGroupEntity;
import com.checkup.pojo.TCheckgroup;
import com.checkup.service.TCheckgroupService;
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
@RequestMapping("/checkgroup")
public class TCheckgroupController {

    @Reference
    private TCheckgroupService tCheckgroupService;

    //分页列表查询
    @RequestMapping("findPage")
    public Result findPage(@RequestBody QueryPageBean pageBean){
        try {
            IPage<TCheckgroup> page = tCheckgroupService.findPage(pageBean);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, page);
        } catch (Exception e){
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL, null);
        }
    }

    //添加检查组
    @RequestMapping("add")
    public Result add(@RequestParam("itemIds") int[] itemIds, @RequestBody TCheckgroup tCheckgroup){
        try {
            tCheckgroupService.save(tCheckgroup, itemIds);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS, null);
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL, null);
        }
    }

    //删除
    @RequestMapping("deleteOne")
    public Result deleteOne(@RequestParam("groupId") int groupId){
        try {
            tCheckgroupService.deleteOne(groupId);
            return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS, null);
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL, null);
        }
    }

    @RequestMapping("findOne")
    public Result findOne(@RequestParam("groupId") int groupId){
        try {
            TCheckGroupEntity tCheckGroupEntity = tCheckgroupService.findOne(groupId);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, tCheckGroupEntity);
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL, null);
        }
    }

    @RequestMapping("edit")
    public Result edit(@RequestParam("itemIds") int[] itemIds, @RequestBody TCheckgroup tCheckgroup){
        try {
            tCheckgroupService.edit(tCheckgroup, itemIds);
            return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS, null);
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL, null);
        }
    }

    @RequestMapping("list")
    public Result list(){
        try {
            List<TCheckgroup> list = tCheckgroupService.list();
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, list);
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL, null);
        }
    }

}

