package com.checkup.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ldq
 * @version 1.0
 * @date 2024-7-4 22:18
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result implements Serializable {

    private Boolean flag;

    private String message;

    private Object obj;

}
