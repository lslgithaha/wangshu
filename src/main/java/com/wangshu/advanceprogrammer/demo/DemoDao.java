package com.wangshu.advanceprogrammer.demo;

import com.wangshu.common.repostory.WangshuDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Create by LSL on 2019\3\12 0012
 * 描述：
 * 版本：1.0.0
 */
@Repository
public interface DemoDao extends WangshuDao<DemoEntity, Integer> {
    DemoEntity save(DemoEntity entity);
    List<DemoEntity> findByNickname(String name);
}
