package com.hnayyc.giftcrawler.service;

import com.hnayyc.giftcrawler.dao.SqlDao;
import com.hnayyc.giftcrawler.model.DoubanBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class DoubanService {

    @Autowired
    SqlDao sqlDao;

    public int insertDoubanBook(DoubanBook doubanBook) {

        String sql = "select 1=1";
        sqlDao.createSQLQueryForMap(sql);
        return 0;
    }
}
