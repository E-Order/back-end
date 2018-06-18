package com.eorder.VO;

import lombok.Data;

@Data
public class ResultVO2<T> {
    /** 错误码 0 . */
    private Integer code;
    /** 提示信息 成功. */
    private String msg;
    /** 返回的具体内容. */
    private long total;
    private T data;
}
