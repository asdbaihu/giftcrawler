package com.hnayyc.giftcrawler.pipeline;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

public class SysoutPipeline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
        System.out.println("YangCheng : " + resultItems.getRequest().getUrl());
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            System.out.println( "YangCheng : " + entry.getKey() + ":\t" + entry.getValue());
        }
    }
}
