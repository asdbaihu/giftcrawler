package com.hnayyc;

import com.hnayyc.giftcrawler.webmagic.DoubanBookPageProcessor;
import com.hnayyc.giftcrawler.webmagic.GithubRepoPageProcessor;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientGenerator;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Hello world!
 */
public class App {

    public static void stlVersion() {
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, null, new java.security.SecureRandom());
            String[] scs = sc.getSocketFactory().getSupportedCipherSuites();
            Arrays.sort(scs);
            for(String s : scs) {
                System.out.println(s);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }
    public static void main( String[] args ) {
        /**
         *  TODO
         *  使用JDK1.8会报出javax.net.ssl.SSLException: Received fatal alert: protocol_version异常
         *  原因是JDK1.8支持TLSv1.2，JDK1.6和JDK1.7支持TLSv1.0，安全协议版本不同造成。
         */
//        Spider.create(new GithubRepoPageProcessor()).addUrl("https://github.com/code4craft").thread(5).run();

        // 数学之美
//        Spider.create(new DoubanBookPageProcessor()).addUrl("https://book.douban.com/subject/10750155").thread(5).run();
        // 阿拉伯的劳伦斯
        Spider.create(new DoubanBookPageProcessor()).addUrl("https://book.douban.com/subject/25883305").thread(5).run();
        // 暂无评分
//        Spider.create(new DoubanBookPageProcessor()).addUrl("https://book.douban.com/subject/30177173/").thread(5).run();
        // 评价人数不足
//        Spider.create(new DoubanBookPageProcessor()).addUrl("https://book.douban.com/subject/27661640/").thread(5).run();
    }
}
