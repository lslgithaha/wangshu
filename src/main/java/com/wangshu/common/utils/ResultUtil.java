package com.wangshu.common.utils;


import com.wangshu.common.exception.WangshuException;
import com.wangshu.common.result.ErrorMsg;
import com.wangshu.common.result.Result;
import com.wangshu.common.result.ResultEnum;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Create by LSL on 2019\3\11 0011
 * 描述：返回值快速生成工具
 * 版本：1.0.0
 */
public class ResultUtil {
    private static final ThreadLocal<Result> re = ThreadLocal.withInitial(() -> new Result(ResultEnum.SUCCESS));
    private ResultUtil(){
    }
    /***
     * 方法名: ResultUtil.getSuccessResult
     * 作者: LSL
     * 创建时间: 14:53 2019\3\11 0011
     * 描述: 成功
     * 参数: [obj]
     * 返回: com.wangshu.common.result.Result
     */
    public static Result getSuccessResult(Object obj){
        Result result = re.get();
        result.setData(obj);
        return result;
    }
    /***
     * 方法名: ResultUtil.getFailResult
     * 作者: LSL
     * 创建时间: 14:53 2019\3\11 0011
     * 描述: 失败
     * 参数: [obj]
     * 返回: com.wangshu.common.result.Result
     */
    public static Result getFailResult(Object obj){
        Result result =re.get().change(ResultEnum.FAILED);
        result.setData(obj);
        return result;
    }
    /***
     * 方法名: ResultUtil.userNoLogin
     * 作者: LSL
     * 创建时间: 14:53 2019\3\11 0011
     * 描述: 需登录
     * 参数: []
     * 返回: com.wangshu.common.exception.WangshuException
     */
    public static WangshuException userNoLogin(){
        return new WangshuException(ResultEnum.NOT_LOGIN);
    }
    /***
     * 方法名: ResultUtil.userNoLogin
     * 作者: LSL
     * 创建时间: 14:53 2019\3\11 0011
     * 描述: 需登录，提示信息自定义
     * 参数: [msg]
     * 返回: com.wangshu.common.exception.WangshuException
     */
    public static WangshuException userNoLogin(String msg){
        return new WangshuException(ResultEnum.NOT_LOGIN, msg);
    }

    /***
     * 方法名: ResultUtil.permissionDenied
     * 作者: LSL
     * 创建时间: 14:58 2019\3\11 0011
     * 描述: 权限不足
     * 参数: []
     * 返回: com.wangshu.common.exception.WangshuException
     */
    public static WangshuException permissionDenied(){
        return new WangshuException(ResultEnum.PERMISSION_DENIED);
    }
    /***
     * 方法名: ResultUtil.userNoLogin
     * 作者: LSL
     * 创建时间: 14:53 2019\3\11 0011
     * 描述: 权限不足，提示消息自定义
     * 参数: [String msg]
     * 返回: com.wangshu.common.exception.WangshuException
     */
    public static WangshuException permissionDenied(String msg){
        return new WangshuException(ResultEnum.PERMISSION_DENIED, msg);
    }

    /***
     * 方法名: ResultUtil.validateError
     * 作者: LSL
     * 创建时间: 14:53 2019\3\11 0011
     * 描述: 参数校验未通过
     * 参数: [msg]
     * 返回: com.wangshu.common.exception.WangshuException
     */
    public static Result validateError(BindException msg){
        List<FieldError> fieldErrors = msg.getBindingResult().getFieldErrors();
        String errors = fieldErrors.stream().map(e -> e.getDefaultMessage()).collect(Collectors.joining(","));
        return re.get().change(ResultEnum.VALIDATE, errors);
    }
    public static Result validateError(ConstraintViolationException msg){
        Set<ConstraintViolation<?>> constraintViolations = msg.getConstraintViolations();
        String errors = constraintViolations.stream().map(e -> e.getMessageTemplate()).collect(Collectors.joining(","));
        return re.get().change(ResultEnum.VALIDATE, errors);
    }

    /***
     * 方法名: ResultUtil.validateError
     * 作者: LSL
     * 创建时间: 14:53 2019\3\11 0011
     * 描述: 条件校验未通过
     * 参数: [msg]
     * 返回: com.wangshu.common.exception.WangshuException
     */
    public static WangshuException conditionError(ErrorMsg msg){
        return new WangshuException(ResultEnum.CONDITION, msg.getMessage());
    }

    /***
     * 方法名: ResultUtil.validateError
     * 作者: LSL
     * 创建时间: 14:53 2019\3\11 0011
     * 描述: 未知异常
     * 参数: [msg]
     * 返回: com.wangshu.common.exception.WangshuException
     */
    public static WangshuException unknownError(){
        return new WangshuException(ResultEnum.EXCEPTION, "未知错误");
    }


    public static Integer getPageSize() {
        return re.get().getPageSize();
    }

    public static void setPageSize(Integer pageSize) {
        re.get().setPageSize(pageSize);
    }

    public static Integer getPageNumber() {
        return re.get().getPageNumber();
    }

    public static void setPageNumber(Integer pageNumber) {
        re.get().setPageNumber(pageNumber);
    }

    public static Integer getTotalPages() {
        return re.get().getTotalPages();
    }

    public static void setTotalPages(Integer totalPages) {
        re.get().setTotalPages(totalPages);
    }

    public static Long getTotalElements() {
        return re.get().getTotalElements();
    }

    public static void setTotalElements(Long totalElements) {
        re.get().setTotalElements(totalElements);
    }
}
