package com.eorder.utils;

import com.eorder.VO.ResultVO2;

public class ResultVO2Util {
    public static ResultVO2 success(long total, Object object) {
        ResultVO2 resultVO = new ResultVO2();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setTotal(total);
        resultVO.setData(object);
        return resultVO;
    }
}
