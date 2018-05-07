package com.hnayyc.giftcrawler.dao;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class SqlDao {

    protected SessionFactory sessionFactory;

    /**
     * 采用@Autowired按类型注入SessionFactory, 当有多个SesionFactory的时候在子类重载本函数.
     */
    @Autowired
    public void setSessionFactory(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * 取得sessionFactory.
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * 取得当前Session.
     */
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public SQLQuery createSQLQueryForMap(final String sqlQueryString, final Object... values) {
        Assert.hasText(sqlQueryString, "queryString不能为空");
        SQLQuery query = getSession().createSQLQuery(sqlQueryString);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }
}
