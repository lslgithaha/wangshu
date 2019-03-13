package com.wangshu.advanceprogrammer.common;

import com.wangshu.common.result.ErrorMsg;

/**
 * Create by LSL on 2019\3\12 0012
 * 描述：
 * 版本：1.0.0
 */
public enum CommonMsg implements ErrorMsg {
    UNDERAGE("未成年人禁止访问本网站"),
    IMAGENOTNULL("图片不得为空！"),
    ;

    private String msg;

    CommonMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }
}
