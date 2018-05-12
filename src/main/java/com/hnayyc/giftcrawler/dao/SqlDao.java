package com.hnayyc.giftcrawler.dao;

import com.hnayyc.giftcrawler.model.DoubanBook;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

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

    /**
     * 执行SQL进行批量修改/删除操作.
     *
     * @param values 数量可变的参数,按顺序绑定.
     * @return 更新记录数.
     */
    public int executeSql(final String sql, final Object... values) {
        return createSQLQuery(sql, values).executeUpdate();
    }

    public SQLQuery createSQLQuery(final String sqlQueryString, final Object... values) {
        Assert.hasText(sqlQueryString, "queryString不能为空");
        SQLQuery query = getSession().createSQLQuery(sqlQueryString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
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

    public void saveDoubanBook(DoubanBook doubanBook) {
        Assert.notNull(doubanBook, "entity不能为空");
        getSession().saveOrUpdate(doubanBook);
    }
}
