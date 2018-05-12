package com.hnayyc.giftcrawler.service;

import com.hnayyc.giftcrawler.dao.SqlDao;
import com.hnayyc.giftcrawler.model.DoubanBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.codecraft.webmagic.ResultItems;


@Transactional
@Service
public class DoubanService {

    @Autowired
    SqlDao sqlDao;

    public void saveDoubanBook(ResultItems resultItems) {
        DoubanBook doubanBook = new DoubanBook();
        doubanBook.setBookId(resultItems.get("bookId"));
        doubanBook.setBookUrl(resultItems.get("bookUrl"));
        doubanBook.setBookName(resultItems.get("bookName"));
        doubanBook.setBookSubName(resultItems.get("bookSubName"));
        doubanBook.setBookOriginName(resultItems.get("bookOriginName"));
        doubanBook.setAuthor(resultItems.get("author"));
        doubanBook.setTranslator(resultItems.get("translator"));
        doubanBook.setPublisher(resultItems.get("publisher"));
        doubanBook.setPublicationDate(resultItems.get("publicationDate"));
        doubanBook.setPages(Integer.parseInt(resultItems.get("pages").toString().trim()));
        doubanBook.setPrice(Float.parseFloat(resultItems.get("price").toString().replace("元", "").trim()));
        doubanBook.setFormat(resultItems.get("format"));
        doubanBook.setIsbn(resultItems.get("isbn"));
        doubanBook.setSeriesId(resultItems.get("seriesId"));
        doubanBook.setSeriesName(resultItems.get("seriesName"));
        doubanBook.setSeriesUrl(resultItems.get("seriesUrl"));
        doubanBook.setTags(resultItems.get("tags"));
        doubanBook.setImagePath(resultItems.get("imagePath"));
        doubanBook.setScore(Float.parseFloat(resultItems.get("score")));
        doubanBook.setVotes(Integer.parseInt(resultItems.get("votes")));
        doubanBook.setStar5(Float.parseFloat(resultItems.get("star5").toString().replace("%", "").trim()));
        doubanBook.setStar4(Float.parseFloat(resultItems.get("star4").toString().replace("%", "").trim()));
        doubanBook.setStar3(Float.parseFloat(resultItems.get("star3").toString().replace("%", "").trim()));
        doubanBook.setStar2(Float.parseFloat(resultItems.get("star2").toString().replace("%", "").trim()));
        doubanBook.setStar1(Float.parseFloat(resultItems.get("star1").toString().replace("%", "").trim()));
        sqlDao.saveDoubanBook(doubanBook);
    }

    public int insertDemo(String code, String name) {
        String sql = "insert into coffee_dict(dict_code, dict_name) values('" + code + "','" + name + "')";
        return sqlDao.executeSql(sql);
    }
}
