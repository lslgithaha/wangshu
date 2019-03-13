package com.wangshu.common.result;

public enum ResultEnum{
    EXCEPTION(-1, "未知异常"), //未知异常
    SUCCESS(0, "success"), // success
    FAILED(1, "failure"), // fail
    NOT_LOGIN(2, "登录后方可访问"),// 用户未登录
    PERMISSION_DENIED(3, "权限被拒绝"), // 权限被拒绝

    VALIDATE(4, ""), // 字段校验类错误
    CONDITION(5, ""),// 条件不符合类错误

    BUSINESSLIMIT(6, "业务受限")
    ;

    private Integer code;
    private String msg;

    ResultEnum(Integer code,String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
