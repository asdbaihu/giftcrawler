package com.hnayyc.giftcrawler;

import com.hnayyc.giftcrawler.jsoup.BaiduPage;
import com.hnayyc.giftcrawler.jsoup.DoubanBook;
import com.hnayyc.giftcrawler.webmagic.GithubRepoPageProcessor;

public class AppMain {
    public static void main(String[] args) {
        //BaiduPage.runBaiduPage(args);
        DoubanBook.runJsoup(args);
        //GithubRepoPageProcessor.runWebmagic(args);
    }
}
