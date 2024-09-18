package com.checkup.controller;


import com.checkup.bean.Result;
import com.checkup.util.MessageConstant;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yjq
 * @since 2021-10-28
 */
@RestController
@RequestMapping("/user")
public class TUserController {

    @RequestMapping("getLoginName")
    public Result getLoginName(){
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, username);
        } catch (Exception e){
            e.printStackTrace();
            return new Result(true, MessageConstant.GET_USERNAME_FAIL, null);
        }
    }

}

