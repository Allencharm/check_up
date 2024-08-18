package com.checkup.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.checkup.bean.QueryPageBean;
import com.checkup.bean.Result;
import com.checkup.entity.TSetmealEntity;
import com.checkup.pojo.TSetmeal;
import com.checkup.service.TSetmealService;
import com.checkup.util.MessageConstant;
import com.checkup.util.RedisConstant;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yjq
 * @since 2021-10-28
 */
@RestController
@RequestMapping("/setmeal")
public class TSetmealController {

    @Reference
    private TSetmealService tSetmealService;

    @Value("${setmeal_img_path}")
    private String path;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //分页列表查询
    @RequestMapping("findPage")
    public Result findPage(@RequestBody QueryPageBean pageBean){
        try {
            IPage<TSetmeal> page = tSetmealService.findPage(pageBean);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, page);
        } catch (Exception e){
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL, null);
        }
    }

    //图片上传
    @RequestMapping("upload")
    public Result upload(MultipartFile imgFile){
        try {
            if (imgFile != null && !imgFile.isEmpty()){
                String originalFilename = imgFile.getOriginalFilename();
                int indexOf = originalFilename.lastIndexOf(".");
                String extName = originalFilename.substring(indexOf);
                String newName = UUID.randomUUID().toString().replaceAll("-", "") + extName;
                //String path = "C:\\Users\\dell\\Desktop\\checkup";
                File file = new File(path, newName);
                imgFile.transferTo(file);

                //向redis缓存上传成功的文件名
                stringRedisTemplate.boundSetOps(RedisConstant.UPLOAD_KEY).add(newName);

                return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, newName);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return new Result(true, MessageConstant.PIC_UPLOAD_FAIL, null);
    }

    //添加功能
    @RequestMapping("add")
    public Result add(@RequestParam("groupIds") int[] groupIds, @RequestBody TSetmeal tSetmeal){
        try {
            tSetmealService.save(tSetmeal,groupIds);

            //向redis缓存添加成功的文件名
            stringRedisTemplate.boundSetOps(RedisConstant.Add_KEY).add(tSetmeal.getImg());
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS, null);
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL, null);
        }
    }

    @RequestMapping("delete")
    public Result delete(@RequestParam("setmealId") int setmealId){
        try {
            tSetmealService.delete(setmealId);
            return new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS, null);
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_SETMEAL_FAIL, null);
        }
    }

    //数据回显
    @RequestMapping("findOne")
    public Result findOne(@RequestParam("setmealId") int setmealId){
        try {
            TSetmealEntity tSetmealEntity = tSetmealService.findOne(setmealId);
            return new Result(true, MessageConstant.EDIT_SETMEAL_SUCCESS, tSetmealEntity);
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_SETMEAL_FAIL, null);
        }
    }

    //数据回显
    @RequestMapping("edit")
    public Result edit(@RequestParam("groupIds") int[] groupIds,@RequestParam("img") String img, @RequestBody TSetmeal tSetmeal){
        try {
            tSetmealService.updateById(tSetmeal, groupIds, img);
            stringRedisTemplate.boundSetOps(RedisConstant.Add_KEY).add(tSetmeal.getImg());
            stringRedisTemplate.boundSetOps(RedisConstant.Add_KEY).remove(img);
            return new Result(true, MessageConstant.EDIT_SETMEAL_SUCCESS, null);
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_SETMEAL_FAIL, null);
        }
    }
}

