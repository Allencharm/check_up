package com.checkup.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
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
public class TCheckgroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String code;

    private String name;

    @TableField("helpCode")
    private String helpcode;

    private String sex;

    private String remark;

    private String attention;


}
