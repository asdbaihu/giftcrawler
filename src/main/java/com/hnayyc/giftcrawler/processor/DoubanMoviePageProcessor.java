package com.hnayyc.giftcrawler.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 豆瓣电影
 */
public class DoubanMoviePageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    @Override
    public void process(Page page) {

    }

    @Override
    public Site getSite() {
        return site;
    }
}
