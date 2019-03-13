package com.wangshu.common.exception;

import com.wangshu.common.result.ResultEnum;

/**
 * Create by LSL on 2019\3\11 0011
 * 描述：wanghu 的 base 异常
 * 版本：1.0.0
 */
public class WangshuException extends RuntimeException {
    private ResultEnum status;
    public WangshuException(ResultEnum re){
        super(re.getMsg());
        this.status = re;
    }
    public WangshuException(ResultEnum re,String msg){
        super(msg);
        this.status = re;
    }

    public ResultEnum getRnum() {
        return status;
    }
}
