package com.hnayyc.giftcrawler.webmagic;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * 豆瓣图书
 *
 * 抓取数据的时候当抓取的数据量太大的时候，豆瓣的服务器会检测到，并把ip封掉，
 * 但是用浏览器缺仍然可以访问，所以猜测是服务器对HTTP请求中的cookies进行了检测，
 * 在代码中加上cookies，ip就不会再被封了，另外至于会不会是请求时间间隔太短被封ip，
 * 没有具体验证，保险起见每个请求结束之后休眠1s。
 */
public class DoubanBookPageProcessor implements PageProcessor {

    private String imgDir = "D:\\douban\\book";
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    @Override
    public void process(Page page) {

        ResultItems item = new ResultItems();

        String bookUrl = page.getUrl().toString();
        String subject = bookUrl.substring(bookUrl.indexOf("subject/") + 8);
        Html html = page.getHtml();

        // 获取图书基本信息
        String bookName = html.xpath("//h1/span/text()").toString();
        item.put("bookId", subject);
        item.put("bookeUrl", bookUrl);
        item.put("bookName", bookName);

        Element bookInfo = html.getDocument().getElementById("info");
        String infoHtml = bookInfo.html();
        String[] spanArry = infoHtml.split("<br>");
        String line, subLine, key, value;
        for(int i=0; i<spanArry.length; i++) {
            line = spanArry[i].replace("\r", "").replace("\n", "").trim();
            if(line.indexOf("</a>") >= 0) { // 处理包含<a href='https://book.douban.com/series/23971'>超链接的图书信息
                subLine = line.substring(line.indexOf("<a") + 2);
                key = line.substring(line.indexOf("<span class=\"pl\">")+"<span class=\"pl\">".length(), line.indexOf("</span>"));
                value = subLine.substring(subLine.indexOf(">")+1, subLine.indexOf("</a>"));
                System.out.println(key + value);
                if(subLine.indexOf("丛书") >= 0) {
                    String tmp = subLine.substring(subLine.indexOf("href=\"")+6);
                    key = "丛书URL：";
                    value = tmp.substring(0, tmp.indexOf("\""));
                    System.out.println(key + value);
                }
            }
            else { // 处理单纯文字的图书信息
                key = line.substring(line.indexOf("<span class=\"pl\">")+"<span class=\"pl\">".length(), line.indexOf("</span>"));
                value = line.substring(line.indexOf("</span>") + 7);
                System.out.println(key + value);
            }
        }

        // 获取图书评分信息
//        page.putField("图书评分", html.xpath("//div[@class='rating_wrap clearbox']//strong[@class='ll rating_num']/text()").toString().trim());
//        page.putField("投票人数", html.xpath("//div[@class='rating_wrap clearbox']//span[@property='v:votes']/text()").toString().trim());
//        String rate = html.xpath("//div[@class='rating_wrap clearbox']//strong[@class='ll rating_num']/text()").toString().trim();
//        String votes = html.xpath("//div[@class='rating_wrap clearbox']//span[@property='v:votes']/text()").toString().trim();
        Selectable scoreDom = html.xpath("//div[@class='rating_wrap clearbox']//strong[@class='ll rating_num']/text()");
        Selectable votesDom = html.xpath("//div[@class='rating_wrap clearbox']//span[@property='v:votes']/text()");
        String score = scoreDom == null ? "" : scoreDom.toString().trim();
        String votes = votesDom == null ? "" : votesDom.toString();
        if(votes != null) {  // 豆瓣图书评分有三种情况：暂无评分，评价人数不足，正常评分
            System.out.println("图书评分：" + score);
            System.out.println("投票人数：" + votes);
            item.put("score", score);
            item.put("votes", votes);
            int i = 5;
            List<Selectable> lst = html.xpath("//div[@class='rating_wrap clearbox']//span[@class='rating_per']").nodes();
            for(Selectable s : lst) {
                String starScore = s.xpath("//span/text()").toString().trim();
                System.out.println("star rate : " + starScore);
                item.put("star"+i, starScore);
                i++;
            }
        }
        else {
            item.put("score", "0");
            item.put("votes", "0");
            item.put("star5", "0");
            item.put("star4", "0");
            item.put("star3", "0");
            item.put("star2", "0");
            item.put("star1", "0");
        }

        // 抓取图书标签
        String tags = "";
        List<Selectable> tagList = html.xpath("//div[@id='db-tags-section']//a[@class='tag']").nodes();
        for(Selectable tag : tagList) {
            String tagStr = tag.xpath("//a/text()").toString();
            System.out.println("tag : " + tagStr);
            tags = tags + tagStr + ";";
        }
        item.put("tags", tags);


/*
        // 抓取图书封面图片
        String imgUrlStr = html.xpath("//div[@id='mainpic']//img//@src").toString();
        String[] tempArry = imgUrlStr.split("/");
        String imgName = tempArry[tempArry.length - 1];
        String postfix = imgName.substring(imgName.indexOf(".")); // .jpg or .png
        String imgPath = imgDir + "\\" + subject + postfix;
        System.out.println(imgUrlStr);
        System.out.println(imgPath);
        try {
            URL url = new URL(imgUrlStr);
            URLConnection con = url.openConnection();
            InputStream inStream = con.getInputStream();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int len = 0;
            while((len = inStream.read(buf)) != -1){
                outStream.write(buf,0,len);
            }
            inStream.close();
            outStream.close();
            File file = new File(imgPath);
            FileOutputStream op = new FileOutputStream(file);
            op.write(outStream.toByteArray());
            op.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
    }

    @Override
    public Site getSite() {
        return site;
    }
}
