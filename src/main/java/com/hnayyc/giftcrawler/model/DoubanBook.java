package com.hnayyc.giftcrawler.model;

import java.util.Date;

public class DoubanBook {

    private String name; // 书名
    private String publisher;  // 出版社
    private String author;  // 作者
    private String subName;  // 副标题
    private String originName;  // 原作名
    private String translator;   // 译者
    private Date publicationDate;  // 出版年
    private Integer pages;  // 页数
    private Float price;  // 价格
    private String format; // 装帧
    private String seriesName; // 丛书
    private String seriesUrl;  // 丛书链接
    private String isbn;  // ISBN

    private Float score = 0f;  // 评分
    private Integer votes = 0;  // 参评人数
    private Float star5 = 0f;  // 5星评分占比
    private Float star4 = 0f;  // 4星评分占比
    private Float star3 = 0f;  // 3星评分占比
    private Float star2 = 0f;  // 2星评分占比
    private Float star1 = 0f;  // 1星评分占比

    private String tags; // 标签
}
