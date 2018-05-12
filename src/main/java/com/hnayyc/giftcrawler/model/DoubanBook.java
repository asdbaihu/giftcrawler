package com.hnayyc.giftcrawler.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="douban_book")
public class DoubanBook implements Serializable {

    // TODO 为什么必须要有ID字段才能似乎用model类？

    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "book_id", nullable = true)
    private String bookId;   // 图书subject
    @Column(name = "book_url", nullable = true)
    private String bookUrl;  // 图书URL
    @Column(name = "book_name", nullable = true)
    private String bookName; // 图书名称
    @Column(name = "book_sub_name", nullable = true)
    private String bookSubName;  // 图书副标题
    @Column(name = "book_origin_name", nullable = true)
    private String bookOriginName;  // 图书原作名
    @Column(name = "author", nullable = true)
    private String author;  // 作者
    @Column(name = "translator", nullable = true)
    private String translator;   // 译者
    @Column(name = "publisher", nullable = true)
    private String publisher;  // 出版社
    @Column(name = "publication_date", nullable = true)
    private String publicationDate;  // 出版年
    @Column(name = "pages", nullable = true)
    private Integer pages;  // 页数
    @Column(name = "price", nullable = true)
    private Float price;  // 价格
    @Column(name = "format", nullable = true)
    private String format; // 装帧
    @Column(name = "isbn", nullable = true)
    private String isbn;  // ISBN
    @Column(name = "series_name", nullable = true)
    private String seriesName; // 丛书
    @Column(name = "series_url", nullable = true)
    private String seriesUrl;  // 丛书链接
    @Column(name = "series_id", nullable = true)
    private String seriesId;   // 丛书ID
    @Column(name = "tags", nullable = true)
    private String tags; // 标签
    @Column(name = "score", nullable = true)
    private Float score = 0f;  // 评分
    @Column(name = "votes", nullable = true)
    private Integer votes = 0;  // 参评人数
    @Column(name = "star5", nullable = true)
    private Float star5 = 0f;  // 5星评分占比
    @Column(name = "star4", nullable = true)
    private Float star4 = 0f;  // 4星评分占比
    @Column(name = "star3", nullable = true)
    private Float star3 = 0f;  // 3星评分占比
    @Column(name = "star2", nullable = true)
    private Float star2 = 0f;  // 2星评分占比
    @Column(name = "star1", nullable = true)
    private Float star1 = 0f;  // 1星评分占比
    @Column(name = "image_path", nullable = true)
    private String imagePath;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookSubName() {
        return bookSubName;
    }

    public void setBookSubName(String bookSubName) {
        this.bookSubName = bookSubName;
    }

    public String getBookOriginName() {
        return bookOriginName;
    }

    public void setBookOriginName(String bookOriginName) {
        this.bookOriginName = bookOriginName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getSeriesUrl() {
        return seriesUrl;
    }

    public void setSeriesUrl(String seriesUrl) {
        this.seriesUrl = seriesUrl;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public Float getStar5() {
        return star5;
    }

    public void setStar5(Float star5) {
        this.star5 = star5;
    }

    public Float getStar4() {
        return star4;
    }

    public void setStar4(Float star4) {
        this.star4 = star4;
    }

    public Float getStar3() {
        return star3;
    }

    public void setStar3(Float star3) {
        this.star3 = star3;
    }

    public Float getStar2() {
        return star2;
    }

    public void setStar2(Float star2) {
        this.star2 = star2;
    }

    public Float getStar1() {
        return star1;
    }

    public void setStar1(Float star1) {
        this.star1 = star1;
    }
}
