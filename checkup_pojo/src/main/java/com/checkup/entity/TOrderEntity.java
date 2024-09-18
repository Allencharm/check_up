package com.checkup.entity;

import com.checkup.pojo.TOrder;
import lombok.Data;

@Data
public class TOrderEntity extends TOrder {
    private String member;
    private String setmeal;
}
