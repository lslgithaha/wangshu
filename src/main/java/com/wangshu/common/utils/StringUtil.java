package com.wangshu.common.utils;

import java.util.Arrays;

/**
 * Create by LSL on 2019\3\11 0011
 * 描述：
 * 版本：1.0.0
 */
public class StringUtil {
    /***
     * 方法名: StringUtils.isEmpty
     * 作者: LSL
     * 创建时间: 14:17 2019\3\11 0011
     * 描述: 字符串判空，"   " 算有值
     * 参数: [str]
     * 返回: boolean
     */
    public static boolean isEmpty(String str){
        return null == str || "".equals(str);
    }
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }
    /***
     * 方法名: StringUtils.isBlack
     * 作者: LSL
     * 创建时间: 14:19 2019\3\11 0011
     * 描述: 字符串判空，"   " 算空
     * 参数: [str]
     * 返回: boolean
     */
    public static boolean isBlack(String str){
        if (str == null)
            return true;
        return isEmpty(str.trim());
    }
    public static boolean isNotBlack(String str){
        return !isBlack(str);
    }
}
