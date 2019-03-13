package com.wangshu.advanceprogrammer.demo;

import com.wangshu.common.exception.WangshuException;
import com.wangshu.common.result.Result;
import com.wangshu.common.result.ResultEnum;
import com.wangshu.common.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Create by LSL on 2019\3\11 0011
 * 描述：
 * 版本：1.0.0
 */
@RestController
public class DemoController {
    @Autowired
    private DemoService service;
    @GetMapping("/xxx")
    public Result wwq(@Validated DemoEntity rntity, HttpServletRequest request){
//        if (true){
//            throw ResultUtil.permissionDenied();
//        }
//        DemoEntity demoEntity = new DemoEntity();
//        demoEntity.setAge(19);
//        demoEntity.setName("阿萨");
//        service.save(rntity);
        return ResultUtil.getSuccessResult(service.findByName(rntity));
    }
}
