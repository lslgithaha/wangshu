package com.wangshu.common.repostory;

import com.wangshu.common.utils.ResultUtil;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Create by LSL on 2019\3\12 0012
 * 描述：
 * 版本：1.0.0
 */
public class WangshuDaoImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements WangshuDao<T, ID> {

    private final EntityManager em;

    public WangshuDaoImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.em = em;
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> page() {
        return super.findAll(getPage()).getContent();
    }


    private List<T> dynamic(T t, boolean page,String...orders) {
        CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
        CriteriaQuery<?> query = criteriaBuilder.createQuery(t.getClass());
        Root<?> from = query.from(t.getClass());

        List<Predicate> conditions = new ArrayList<>();
        Map<String, Object> filedInfo = getFiledInfo(t); // 字段 --> 值
        for (String s : filedInfo.keySet()) {
            if (filedInfo.get(s) != null) {
                if(filedInfo.get(s) instanceof String && filedInfo.get(s).toString().indexOf("%") != -1){
                    conditions.add(criteriaBuilder.like(from.get(s), filedInfo.get(s).toString()));
                }else{
                    conditions.add(criteriaBuilder.equal(from.get(s), filedInfo.get(s)));
                }
            }
        }
        //添加条件
        if(conditions != null && conditions.size() > 0){
            query.where(conditions.toArray(new Predicate[conditions.size()]));
        }
        List<Order> os = new ArrayList<>();
        for (String or:orders){
            String[] split = or.split(",");
            if (split[1].equalsIgnoreCase("asc"))
                os.add(criteriaBuilder.asc(from.get(split[0])));
            else if (split[1].equalsIgnoreCase("desc"))
                os.add(criteriaBuilder.desc(from.get(split[0])));
        }
        CriteriaQuery<?> criteriaQuery = query.orderBy(os);
        TypedQuery<?> tq = em.createQuery(criteriaQuery);
        //设置分页
        if(page){
            tq.setFirstResult(ResultUtil.getPageNumber());
            tq.setMaxResults(ResultUtil.getPageSize());
        }
        return (List<T>) tq.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> dynamic(T t, String...orders) {
        return dynamic(t, false,orders);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> dynamicPage(T t, String...orders) {
        return dynamic(t, true,orders);
    }

    @Override
    public List<T> dynamicLike(T t, String... orders) {
        return dynamic(t, true,orders);
    }
}
