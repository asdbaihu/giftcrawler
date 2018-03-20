package com.hnayyc.giftcrawler.jsoup;

import java.util.concurrent.TimeUnit;

/**
 * Created by YangCheng on 2017/3/2.
 */
public class BaiduPage {
    public static void runBaiduPage(String[] args) {
        final BaiduWorker worker = new BaiduWorker();
        worker.addStartAddress("www.baidu.com");
        System.out.println("任务开始");
        new Thread(new Runnable() {
            //@Override
            public void run() {
                worker.start();
            }
        }).start();

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        worker.stop();
    }
}
