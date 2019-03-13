package com.wangshu.common.repostory;

import com.wangshu.common.exception.WangshuException;
import com.wangshu.common.result.ResultEnum;
import com.wangshu.common.utils.ResultUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoRepositoryBean
public interface WangshuDao<T, ID> extends JpaRepository<T, ID> {
    List<T> page();
    List<T> dynamic(T t, String...orders);
    List<T> dynamicPage(T t, String...orders);
    List<T> dynamicLike(T t, String...orders);
    default PageRequest getPage(){
        return PageRequest.of(ResultUtil.getPageNumber(), ResultUtil.getPageSize());
    }
    default Map<String, Object> getFiledInfo(T t){
        Map<String, Object> map = new HashMap<>();
        if(t == null)
            return map;
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            //打开私有访问
            field.setAccessible(true);
            //获取属性
            String name = field.getName();
//            Column column = field.getAnnotation(Column.class);
//            if(column != null && StringUtil.isNotBlack(column.name()))
//                name = column.name();
            //获取属性值
            try {
                Object value = field.get(t);
                map.put(name, value);
            } catch (IllegalAccessException e) {
                throw new WangshuException(ResultEnum.EXCEPTION, "获取实体字段信息异常");
            }
        }
        return map;
    }

    enum OrderBy{
        ASC(true),
        DESC(false);
        private boolean asc;

        OrderBy(boolean v) {
            this.asc = v;
        }

        public boolean isAsc() {
            return asc;
        }
    }
}
