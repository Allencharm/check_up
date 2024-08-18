package com.checkup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.checkup.dao.ReportMapper;
import com.checkup.dao.TMemberMapper;
import com.checkup.pojo.TMember;
import com.checkup.service.ReportService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.soap.SAAJResult;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private TMemberMapper memberMapper;

    @Autowired
    private ReportMapper reportMapper;

    //统计会员数量
    @Override
    public Map getMemberReport() {
        //当前系统时间
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        calendar.set(Calendar.MONTH,month - 13);

        String[] months = new String[12];
        int[] memberCount = new int[12];
        for(int i = 1;i <= 12;i++){
            month = calendar.get(Calendar.MONTH);
            calendar.set(Calendar.MONTH,month + 1);
            int year1 = calendar.get(Calendar.YEAR);
            int month1 = calendar.get(Calendar.MONTH) + 1;
            //System.out.println(year1 + "-" + month1 + "-31");
            String str = year1 + "-" + month1 + "-31";
            months[i - 1] = year1 + "-" + month1;

            QueryWrapper<TMember>wrapper = new QueryWrapper<>();
            wrapper.le("regTime",str);
            Integer count = memberMapper.selectCount(wrapper);
            memberCount[i - 1] = count;
        }

        Map map = new HashMap();
        map.put("months",months);
        map.put("memberCount",memberCount);
        return map;
    }

    //统计每种套餐的订单数
    @Override
    public Map getSetmealReport() {
        List<Map> list = reportMapper.selectSetmealReport();
        List<String> setmealNames = new ArrayList<>();
        for(Map map : list){
            Object value = map.get("name");
            setmealNames.add(value+"");
        }

        Map map = new HashMap();
        map.put("setmealNames",setmealNames);
        map.put("setmealCount",list);
        return map;
    }
}
