package com.checkup.service;

import com.checkup.pojo.TOrdersetting;
import com.baomidou.mybatisplus.extension.service.IService;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yjq
 * @since 2021-10-28
 */
public interface TOrdersettingService extends IService<TOrdersetting> {

    void importExcelToMysql(List<String[]> list)throws Exception;

    List<Map> findByYearAndMonth(String year, String month);

    void update(String date, int num);
}
