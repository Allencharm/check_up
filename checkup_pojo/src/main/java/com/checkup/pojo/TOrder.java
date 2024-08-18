package com.checkup.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author yjq
 * @since 2021-10-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 员会id
     */
    private Integer memberId;

    /**
     * 约预日期
     */
    @TableField("orderDate")
    private LocalDate orderdate;

    /**
     * 约预类型 电话预约/微信预约
     */
    @TableField("orderType")
    private String ordertype;

    /**
     * 预约状态（是否到诊）
     */
    @TableField("orderStatus")
    private String orderstatus;

    /**
     * 餐套id
     */
    private Integer setmealId;


}
