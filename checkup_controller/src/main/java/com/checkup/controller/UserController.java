package com.checkup.controller;

import com.checkup.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ldq
 * @version 1.0
 * @date 2024-6-23 13:10
 * @Description:
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Reference
    private UserService userService;

    @RequestMapping("first")
    public String first(){
        return userService.getString();
    }
}
