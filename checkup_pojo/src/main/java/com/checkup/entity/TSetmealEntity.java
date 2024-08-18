package com.checkup.entity;

import com.checkup.pojo.TSetmeal;
import com.checkup.pojo.TSetmealCheckgroup;
import lombok.Data;

import java.util.List;

/**
 * @author ldq
 * @version 1.0
 * @date 2024-8-4 22:58
 * @Description:
 */
@Data
public class TSetmealEntity extends TSetmeal {
    private List<Object> checkgroupIds;
}
