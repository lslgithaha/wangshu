package com.wangshu.common.aspect;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wangshu.common.exception.WangshuException;
import com.wangshu.common.result.Result;
import com.wangshu.common.utils.ResultUtil;
import com.wangshu.common.utils.StringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Create by LSL on 2019\3\11 0011
 * 描述：记录接口访问日志
 * 版本：1.0.0
 */
@Aspect
@Component
public class WebLogAspect {
    private final static Logger logger = LoggerFactory.getLogger(WebLogAspect.class);
    @Pointcut("@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController)")
    public void webLog() {
    }

    @Around("webLog()")
    public Object doBasicAround(ProceedingJoinPoint pjp) throws Throwable {
        WebLogEntity entity = new WebLogEntity();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        entity.setStartTime(System.currentTimeMillis()); // 访问接口时间

        String url = request.getServletPath();
        entity.setUrl(url); // 路径

        String method = request.getMethod();
        entity.setType(method); // 请求类型

        String remoteAddr = request.getRemoteAddr();
        entity.setRemoteAddr(remoteAddr); // 来访IP

        String class_method = pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName();
        entity.setMethod(class_method); // 执行的方法

        String[] parameterNames = ((MethodSignature) pjp.getSignature()).getParameterNames();
        Object[] args = pjp.getArgs();
        HashMap<String, Object> pmap = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            if(args[i] instanceof ServletRequest || args[i] instanceof ServletResponse
                    || args[i] instanceof HttpSession || args[i] instanceof ServletContext){
                pmap.put(parameterNames[i], args[i].getClass().getSimpleName());
            }else {
                pmap.put(parameterNames[i], args[i]);
            }

        }
        entity.setParam(JSONObject.toJSONString(pmap) ); // 参数


        Object retVal = null;
        try {
            retVal = pjp.proceed(); // 执行
            entity.setMark(0); // 设置执行标记 (-1 -> 异常)  (0 -> 成功) (1 ->不满足条件而失败)
        } catch (Throwable throwable) {
            entity.setExceptionMsg(throwable.getMessage());
            if(throwable instanceof WangshuException){
                entity.setMark(1); // 设置执行标记 (-1 -> 异常)  (0 -> 成功) (1 ->不满足条件而失败)
            }else {
                entity.setMark(-1); // 设置执行标记 (-1 -> 异常)  (0 -> 成功) (1 ->不满足条件而失败)
            }
            throw throwable;
        } finally {

            long endTime = System.currentTimeMillis();
            entity.setEndTime(endTime); // 执行完成时间

            long takeTime = endTime - entity.getStartTime();
            entity.setTaketime((int) takeTime); // 花费时间
            entity.setResult(JSONObject.toJSONString(retVal, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullStringAsEmpty)); // 返回值
            logger.info("ip:{},url:{},访问类型：{},参数：{},方法：{},消耗时间：{}毫秒,执行结果:{},返回值：{}"
                    ,remoteAddr, url, method, entity.getParam(), class_method, takeTime,entity.getMark(),entity.getResult());

            //System.out.println(JSONObject.toJSONString(entity, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullStringAsEmpty));
        }
        return retVal;
    }

    @Before("webLog()")
    public void doBefore(){ // 设置分页信息
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String pageSize = request.getParameter("pageSize");
        String pageNumber = request.getParameter("pageNumber");
        if(StringUtil.isBlack(pageNumber))
            pageNumber = "0";
        if(StringUtil.isBlack(pageSize))
            pageSize = "0";
        ResultUtil.setPageNumber(Integer.valueOf(pageNumber));
        ResultUtil.setPageSize(Integer.parseInt(pageSize));
    }

    @AfterReturning(pointcut = "webLog()", returning = "result")
    public void doAfterReturning(Result result){
        result.setTotalPages(ResultUtil.getTotalPages());
        result.setTotalElements(ResultUtil.getTotalElements());
    }
}
