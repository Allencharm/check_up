package com.checkup.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class TCheckitem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String code;

    private String name;

    private String sex;

    private String age;

    private Float price;

    /**
     * 查检项类型,分为检查和检验两种
     */
    private String type;

    private String attention;

    private String remark;


}
