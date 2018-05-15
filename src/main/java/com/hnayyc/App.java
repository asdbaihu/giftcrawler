package com.hnayyc;

import com.hnayyc.giftcrawler.pipeline.DbPipeline;
import com.hnayyc.giftcrawler.processor.DoubanBookPageProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import us.codecraft.webmagic.Spider;

import javax.net.ssl.SSLContext;
import java.io.*;
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

    /**
     * 重命名文件
     * TODO 添加递归遍历指定目录下的所有文件，可以通过文件后缀名过滤。
     */
    public static void remoteFileName() {
        String filePath = "D:\\yc0509.txt";
        try {
            File file = new File(filePath);
            if (file.exists()) {
                System.out.println(file.getAbsolutePath());
                System.out.println(file.getName());
                file.renameTo(new File("D:\\yc2018-05-09.txt"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main( String[] args ) {
        /**
         *  TODO
         *  使用JDK1.8会报出javax.net.ssl.SSLException: Received fatal alert: protocol_version异常
         *  原因是JDK1.8支持TLSv1.2，JDK1.6和JDK1.7支持TLSv1.0，安全协议版本不同造成。
         */
//        Spider.create(new GithubRepoPageProcessor()).addUrl("https://github.com/code4craft").thread(5).run();

        // 暂无评分
//        Spider.create(new DoubanBookPageProcessor()).addUrl("https://book.douban.com/subject/30177173/").thread(5).run();
        // 评价人数不足
//        Spider.create(new DoubanBookPageProcessor()).addUrl("https://book.douban.com/subject/27661640/").thread(5).run();
        // 数学之美
//        Spider.create(new DoubanBookPageProcessor()).addUrl("https://book.douban.com/subject/10750155").thread(5).run();
        // 阿拉伯的劳伦斯
//        Spider.create(new DoubanBookPageProcessor()).addUrl("https://book.douban.com/subject/25883305").thread(5).run();

        // TODO 在Cookie中添加应对豆瓣反爬虫机制的参数
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");
        DbPipeline dbPipeline = (DbPipeline) context.getBean("dbPipeline");
        dbPipeline.printServiceBean();

        Spider.create(new DoubanBookPageProcessor()).addPipeline(dbPipeline).addUrl("https://book.douban.com/subject/26838557").thread(5).run();

//        remoteFileName();
    }
}
