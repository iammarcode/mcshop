package com.marco.mcshop.model.log;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ControllerInfoLog {
    private String username;

    private String url;

    private String method;

    private String ip;

    private Object parameter;

    private Long startTime;

    private Integer spendTime;

    private Object result;
}
