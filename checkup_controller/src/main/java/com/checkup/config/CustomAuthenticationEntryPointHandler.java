package com.checkup.config;

import com.alibaba.fastjson.JSON;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    //匿名访问验证处理
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        Map<String,String> map = new HashMap<>();
        map.put("key","不能匿名访问");
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(map));
    }
}
