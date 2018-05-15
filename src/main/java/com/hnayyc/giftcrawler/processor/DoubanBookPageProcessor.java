package com.hnayyc.giftcrawler.processor;

import org.jsoup.nodes.Element;
import us.codecraft.webmagic.Page;
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
 * 但是用浏览器缺仍然可以访问，所以猜测是服务器对HTTP请求中的Cookies进行了检测，
 * 在代码中加上Cookies，IP就不会再被封了，另外至于会不会是请求时间间隔太短被封IP，
 * 没有具体验证，保险起见每个请求结束之后休眠1s。
 */
public class DoubanBookPageProcessor implements PageProcessor {

    private String imgDir = "D:\\douban\\book";
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    @Override
    public void process(Page page) {
        String bookUrl = page.getUrl().toString();
        String subject = bookUrl.substring(bookUrl.indexOf("subject/") + 8);
        Html html = page.getHtml();

        // 获取图书基本信息
        String bookName = html.xpath("//h1/span/text()").toString();
        page.putField("bookId", subject);
        page.putField("bookUrl", bookUrl);
        page.putField("bookName", bookName);

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
                parseBookInfo(page, key, value);
                if(subLine.indexOf("丛书") >= 0) { // 如果有所属丛书，抓取丛书信息。
                    String tmp = subLine.substring(subLine.indexOf("href=\"")+6);
                    // key = "丛书URL：";
                    value = tmp.substring(0, tmp.indexOf("\""));
                    page.putField("seriesUrl", value);
                    page.putField("seriesId", value.substring(value.indexOf("series/") + 7));
                }
            }
            else { // 处理单纯文字的图书信息
                key = line.substring(line.indexOf("<span class=\"pl\">")+"<span class=\"pl\">".length(), line.indexOf("</span>"));
                value = line.substring(line.indexOf("</span>") + 7);
                parseBookInfo(page, key, value);
            }
        }

        // 获取图书评分信息
        Selectable scoreDom = html.xpath("//div[@class='rating_wrap clearbox']//strong[@class='ll rating_num']/text()");
        Selectable votesDom = html.xpath("//div[@class='rating_wrap clearbox']//span[@property='v:votes']/text()");
        String score = scoreDom == null ? "" : scoreDom.toString().trim();
        String votes = votesDom == null ? "" : votesDom.toString();
        if(votes != null) {  // 豆瓣图书有评分情况：正常评分
            page.putField("score", score);
            page.putField("votes", votes);
            int i = 5;
            List<Selectable> lst = html.xpath("//div[@class='rating_wrap clearbox']//span[@class='rating_per']").nodes();
            for(Selectable s : lst) {
                String starScore = s.xpath("//span/text()").toString().trim();
                page.putField("star"+i, starScore);
                i--;
            }
        }
        else { // 豆瓣图书无评分情况：暂无评分，评价人数不足
            page.putField("score", "0");
            page.putField("votes", "0");
            page.putField("star5", "0");
            page.putField("star4", "0");
            page.putField("star3", "0");
            page.putField("star2", "0");
            page.putField("star1", "0");
        }

        // 抓取图书标签
        String tags = "";
        List<Selectable> tagList = html.xpath("//div[@id='db-tags-section']//a[@class='tag']").nodes();
        for(Selectable tag : tagList) {
            String tagStr = tag.xpath("//a/text()").toString();
            tags = tags + tagStr + ";";
        }
        page.putField("tags", tags);

        // 抓取图书封面图片
        String imgUrlStr = html.xpath("//div[@id='mainpic']//img//@src").toString();
        String[] tempArry = imgUrlStr.split("/");
        String imgName = tempArry[tempArry.length - 1];
        String postfix = imgName.substring(imgName.indexOf(".")); // .jpg or .png
        String imgPath = imgDir + "\\" + subject + postfix;
        page.putField("imagePath", imgPath);
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
    }

    @Override
    public Site getSite() {
        return site;
    }

    private void parseBookInfo(Page page, String key, String value) {
        if(key.indexOf("作者") >= 0) {
            page.putField("author", value);
        }
        else if(key.indexOf("译者") >= 0) {
            page.putField("translator", value);
        }
        else if(key.indexOf("出版社") >= 0) {
            page.putField("publisher", value);
        }
        else if(key.indexOf("副标题") >= 0) {
            page.putField("bookSubName", value);
        }
        else if(key.indexOf("原作名") >= 0) {
            page.putField("bookOriginName", value);
        }
        else if(key.indexOf("出版年") >= 0) {
            page.putField("publicationDate", value);
        }
        else if(key.indexOf("页数") >= 0) {
            page.putField("pages", value);
        }
        else if(key.indexOf("定价") >= 0) {
            page.putField("price", value);
        }
        else if(key.indexOf("装帧") >= 0) {
            page.putField("format", value);
        }
        else if(key.indexOf("丛书") >= 0) {
            page.putField("seriesName", value);
        }
        else if(key.indexOf("ISBN") >= 0) {
            page.putField("isbn", value);
        }
        else {
            page.putField("other", value);
        }
    }
}
