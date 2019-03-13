package com.wangshu.common.repostory;

import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.core.support.TransactionalRepositoryFactoryBeanSupport;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

/**
 * Create by LSL on 2019\3\12 0012
 * 描述：
 * 版本：1.0.0
 */
public class JpaRepositoryFactoryBean<T extends Repository<S, ID>, S, ID extends Serializable> extends TransactionalRepositoryFactoryBeanSupport<T, S, ID> {
    private EntityManager entityManager;

    public JpaRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setMappingContext(MappingContext<?, ?> mappingContext) {
        super.setMappingContext(mappingContext);
    }
    protected RepositoryFactorySupport doCreateRepositoryFactory() {
        return this.createRepositoryFactory(this.entityManager);
    }

    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new JpaRepositoryFactory(entityManager);
    }

    public void afterPropertiesSet() {
        Assert.notNull(this.entityManager, "EntityManager must not be null!");
        super.afterPropertiesSet();
    }
}
