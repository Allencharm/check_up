package com.checkup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;

//自定义security适配器
@EnableWebSecurity//允许实现权限管理操作
//允许后端控制器controller层每个方法上面添加角色或者权限验证注解
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //注入认证器类的对象 -- 用户查询，授权、授角色
    @Resource
    private CustomUserDetailService customUserDetailService;
    //注入登录失败处理对象
    @Resource
    private CustomAuthenticationFailureHandler authenticationFailureHandler;
    //注入匿名访问处理对象
    @Resource
    private CustomAuthenticationEntryPointHandler authenticationEntryPointHandler;
    //权限验证失败处理对象
    @Resource
    private CustomAccessDeniedHandler accessDeniedHandler;

    /*@Resource
    private DataSource dataSource;*/

    //加密对象
    @Bean
    public BCryptPasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //登录认证和授权
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //查询页面送来的用户信息，然后给用户授角色和授权限，以及对页面送来的用户的密码做校验
        auth.userDetailsService(customUserDetailService).passwordEncoder(getPasswordEncoder());
    }

    //security的配置策略：指定登录页面、指定登录成功页面、指定登录失败、验证失败如何处理等
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().headers().frameOptions().sameOrigin();//允许fram标签

        //指定登录页面、指定登录成功页面
        http.csrf().disable()//跨站请求伪造 -- 关闭
            .formLogin().loginPage("/index.html")//表单登录，登录页面
            .loginProcessingUrl("/login")//指定登录页面中发出的登录请求url
            .defaultSuccessUrl("/pages/main.html")//登录成功跳转到的页面
            .failureHandler(authenticationFailureHandler)//登录失败如何处理
            .and()
            .authorizeRequests()//请求认证
            .antMatchers("/login","/index.html","ordersetting_template.xlsx").permitAll()//当前项目中哪些url包括资源不需要登录和认证的，可以直接访问
            .antMatchers("/js/**","/css/**","/img/**","/loginstyle/**","/plugins/**").permitAll()//当前项目中所有静态资源js，css，img等可以直接访问，不需要登录不需要校验
            .anyRequest()//除了以上配置，其他任何请求都需要登录和验证权限
            .authenticated();

        //指定验证失败如何处理
        http.exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPointHandler)//匿名访问如何处理
            .accessDeniedHandler(accessDeniedHandler);//验证权限失败如何处理

        //登出操作
        http.logout()
            .logoutUrl("/logout")//登出操作点击的链接url
            .logoutSuccessUrl("/index.html");//登出成功之后跳转到哪个页面
    }
}
