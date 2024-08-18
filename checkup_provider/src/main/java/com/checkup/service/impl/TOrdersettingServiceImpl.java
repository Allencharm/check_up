package com.checkup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.checkup.dao.TOrdersettingMapper;
import com.checkup.pojo.TOrdersetting;
import com.checkup.service.TOrdersettingService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yjq
 * @since 2021-10-28
 */
@Service
public class TOrdersettingServiceImpl extends ServiceImpl<TOrdersettingMapper, TOrdersetting> implements TOrdersettingService {

    @Autowired
    private TOrdersettingMapper ordersettingMapper;

    //导入Excel表数据到MySQL
    @Override
    public void importExcelToMysql(List<String[]> list) throws Exception{
        //遍历集合
        for(String[] strs : list){
            System.out.println(strs[1]);
            System.out.println("--------");
            //判断日期是否存在
            String dateStr = strs[0];
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = simpleDateFormat.parse(dateStr);
            QueryWrapper<TOrdersetting>wrapper = new QueryWrapper<>();
            wrapper.eq("orderDate",date);
            TOrdersetting tOrdersetting = ordersettingMapper.selectOne(wrapper);
            if(tOrdersetting == null){
                System.out.println("=================");
                System.out.println(strs[1]);
                System.out.println("=================");
                //如果不存在添加数据
                TOrdersetting ordersetting = new TOrdersetting();
                LocalDate parse = LocalDate.parse(strs[0], DateTimeFormatter.ofPattern("yyyy/MM/dd"));
                ordersetting.setOrderdate(parse);
                ordersetting.setNumber(Integer.parseInt(strs[1]));

                ordersettingMapper.insert(ordersetting);
            }else {
                System.out.println("******************");
                System.out.println(strs[1]);
                System.out.println("******************");
                //如果存在则修改旧数据-追加
                tOrdersetting.setNumber(tOrdersetting.getNumber() + Integer.parseInt(strs[1]));
                ordersettingMapper.updateById(tOrdersetting);
            }
        }
    }

    //预约日历显示
    @Override
    public List<Map> findByYearAndMonth(String year, String month) {
        String start = year + "-" + month + "-" + "01";
        String end = year + "-" + month + "-" + "31";
        QueryWrapper<TOrdersetting>wrapper = new QueryWrapper<>();
        wrapper.between("orderDate",start,end);
        List<TOrdersetting> list = ordersettingMapper.selectList(wrapper);
        List<Map>orderList = new ArrayList<>();
        for(TOrdersetting ordersetting : list){
            Integer number = ordersetting.getNumber();
            Integer reservations = ordersetting.getReservations();
            LocalDate orderdate = ordersetting.getOrderdate();
            int da = orderdate.getDayOfMonth();

            Map map = new HashMap();
            map.put("date",da);
            map.put("number",number);
            map.put("reservations",reservations);
            orderList.add(map);
        }
        return orderList;
    }

    //修改设置可预约数量
    @Override
    public void update(String date, int num) {
        UpdateWrapper<TOrdersetting>wrapper = new UpdateWrapper<>();
        wrapper.eq("orderDate",date);
        TOrdersetting ordersetting = new TOrdersetting();
        ordersetting.setNumber(num);
        ordersettingMapper.update(ordersetting,wrapper);
    }
}
