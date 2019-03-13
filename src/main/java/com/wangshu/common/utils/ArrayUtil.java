package com.wangshu.common.utils;

import java.util.Collection;
import java.util.Map;

/**
 * Create by LSL on 2019\3\11 0011
 * 描述：数组类工具类
 * 版本：1.0.0
 */
public class ArrayUtil {

    public boolean isEmpty(Object[] objs){
        return objs == null || objs.length < 1;
    }
    public boolean isEmpty(Collection col){
        return col == null || col.size() < 1;
    }
    public boolean isEmpty(Map map){
        return map == null || map.size() < 1;
    }

    public boolean isNotEmpty(Object[] objs){
        return !isEmpty(objs);
    }
    public boolean isNotEmpty(Collection col){
        return !isEmpty(col);
    }
    public boolean isNotEmpty(Map map){
        return !isEmpty(map);
    }
}
