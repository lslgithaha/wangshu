package com.wangshu.common.result;

/**
 * Create by LSL on 2019\3\11 0011
 * 描述：条件不符合提示信息统一存放枚举接口
 * 版本：1.0.0
 */
public interface ErrorMsg{
    default String getMessage(){
        return "";
    };
}
