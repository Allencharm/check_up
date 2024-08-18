package com.checkup.pojo;

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
public class TCheckgroupCheckitem implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer checkgroupId;

    private Integer checkitemId;


}
