package com.wangshu.advanceprogrammer.demo;

import com.wangshu.common.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create by LSL on 2019\3\12 0012
 * 描述：
 * 版本：1.0.0
 */
@Service
public class DemoService{
    @Autowired
    private DemoDao dao;
    public DemoEntity save(DemoEntity entity){
//        dao.save(entity);
//        if(true){
//            throw ResultUtil.unknownError();
//        }
        return dao.save(entity);
    }
    public List<DemoEntity> findByName(DemoEntity entity){
//        List<DemoEntity> page = this.dao.findByName(entity);
        List<DemoEntity> page = dao.dynamicLike(entity,"id,desc");
//        List<DemoEntity> page = dao.dynamic(entity);
        return page;
    }

}
