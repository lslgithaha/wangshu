package com.wangshu.common.APIDiscovery;

import com.wangshu.common.result.Result;
import com.wangshu.common.utils.ResultUtil;
import com.wangshu.common.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.io.InputStreamSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Create by LSL on 2019\3\11 0011
 * 描述：
 * 版本：1.0.0
 */
@Validated
@RestController
@RequestMapping("v1")
public class APIDiscovery {

    @Autowired
    WebApplicationContext applicationContext;

    /***
     * 方法名: APIDiscovery.allAPI
     * 作者: LSL
     * 创建时间: 17:16 2019\3\11 0011
     * 描述: 获得当前所有的API
     * 参数: []
     * 返回: com.wangshu.common.result.Result
     */
    @GetMapping("/apis")
    public Result allAPI(@Pattern(regexp = "^(POST|PUT|GET|DELETE|)$", message = "Type输入错误") String type,
                         String url){
        List<API> apis = getUrls();
        if(StringUtil.isNotBlack(type))
            apis = apis.parallelStream().filter(e -> e.getType().equals(type)).collect(Collectors.toList());
        if(StringUtil.isNotBlack(url))
            apis = apis.parallelStream().filter(e -> e.getUrl().indexOf(url) != -1).collect(Collectors.toList());
        return ResultUtil.getSuccessResult(apis);
    }

    private static final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
    private List<API> getUrls(){
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        List<API> list = new ArrayList<>();
        for (RequestMappingInfo info : map.keySet()) {
            HandlerMethod method = map.get(info);
            String[] patterns = info.getPatternsCondition().getPatterns().toArray(new String[0]);;
            RequestMethod[] methods = info.getMethodsCondition().getMethods().toArray(new RequestMethod[0]);
            if(patterns!=null && methods != null && patterns.length > 0 && methods.length > 0){
                API api = new API();
                api.setUrl(patterns[0]);
                api.setType(methods[0].toString());
                api.setMethod(method.getMethod().getDeclaringClass().getName()+ "." +method.getMethod().getName());
                MethodParameter[] parameters = method.getMethodParameters();
                for (MethodParameter parameter : parameters) {
                    parameter.initParameterNameDiscovery(parameterNameDiscoverer);
                    Class<?> pType = parameter.getParameterType();

                    //记录不需要展开的特殊类名
                    List<String> strings = Arrays.asList(new String[]{"MULTIPARTFILE"});
                    if(pType.isAssignableFrom(ServletRequest.class) || pType.isAssignableFrom(ServletResponse.class)
                            || pType.isAssignableFrom(HttpSession.class) || pType.isAssignableFrom(ServletContext.class)
                            || pType.isAssignableFrom(InputStreamSource.class)|| pType.getName().startsWith("java.lang")||
                            strings.contains(pType.getSimpleName().toUpperCase())) {
                        api.addParm(new Parm(parameter.getParameterName(), pType.getSimpleName()));
                    }else {
                        Map<String, String> entityMap = setAllComponentsName(pType);
                        for (String s : entityMap.keySet()) {
                            api.addParm(new Parm(s, entityMap.get(s)));
                        }
                    }
                }
                list.add(api);
            }
        }
        return list;
    }
    public Map<String, String> setAllComponentsName(Class<?> clz) {
        Map<String, String> hashMap = new HashMap<>();
        // 获取f对象对应类中的所有属性域
        Field[] fields = clz.getDeclaredFields();
        for (int i = 0, len = fields.length; i < len; i++) {
            // 对于每个属性，获取属性名
            String varName = fields[i].getName();
            String tName = fields[i].getType().getSimpleName();
            hashMap.put(tName, varName);
        }
        return hashMap;
    }
    class API{
        private String url;
        private String type;
        private String method;
        private List<Parm> parms = new ArrayList<>();

        public void addParm(Parm parm){
            parms.add(parm);
        }
        public List<Parm> getParms() {
            return parms;
        }

        public void setParms(List<Parm> parms) {
            this.parms = parms;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }
    }
    class Parm{
        private String type;
        private String name;

        public Parm(String type, String name) {
            this.type = type;
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
