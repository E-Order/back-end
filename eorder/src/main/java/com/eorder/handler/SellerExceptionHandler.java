package com.eorder.handler;


import com.eorder.VO.ResultVO;
import com.eorder.enums.ResultEnum;
import com.eorder.exception.SellerException;
import com.eorder.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

@RestControllerAdvice
public class SellerExceptionHandler {

    @Autowired

    //拦截登录异常
    @ExceptionHandler(value = SellerException.class)
    public ResultVO handlerSellerException() {
        return ResultVOUtil.error(ResultEnum.USER_NOT_LOGIN.getCode(),ResultEnum.USER_NOT_LOGIN.getMessage());
    }
}
