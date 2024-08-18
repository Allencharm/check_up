package com.checkup.entity;

import com.checkup.pojo.TCheckgroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author ldq
 * @version 1.0
 * @date 2024-7-21 16:11
 * @Description:
 */
@Data

public class TCheckGroupEntity extends TCheckgroup {

    private List<Object> checkitemIds;

}
