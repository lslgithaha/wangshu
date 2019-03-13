package com.wangshu.common.exception;

import com.wangshu.common.result.Result;
import com.wangshu.common.result.ResultEnum;
import com.wangshu.common.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;


/**
 * Create by LSL on 2019\3\11 0011
 * 描述：
 * 版本：1.0.0
 */
@ControllerAdvice
public class WangshuExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(WangshuExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public Result errorBindException(BindException ex){
        return ResultUtil.validateError(ex);
    }

    @ResponseBody
    @ExceptionHandler(value = ConstraintViolationException.class)
    public Result errorConstraintViolationException(ConstraintViolationException ex){
        return ResultUtil.validateError(ex);
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result errorHandler(Exception ex){
        logger.error("代码发生异常：", ex);
        return new Result(ResultEnum.EXCEPTION, "未知异常");
    }

    @ResponseBody
    @ExceptionHandler(value = WangshuException.class)
    public Result errorWangshuHandler(WangshuException ex){
        return new Result(ex.getRnum(),ex.getMessage());
    }
}
