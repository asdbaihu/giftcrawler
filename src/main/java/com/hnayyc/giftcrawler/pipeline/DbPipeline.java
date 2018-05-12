package com.hnayyc.giftcrawler.pipeline;

import com.hnayyc.giftcrawler.service.DoubanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

@Controller
public class DbPipeline implements Pipeline {

    @Autowired
    private DoubanService doubanService;

    public void printServiceBean() {
        System.out.println(doubanService);
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        System.out.println("DbPipeline : " + resultItems.getRequest().getUrl());
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            System.out.println( "DbPipeline : " + entry.getKey() + ":\t" + entry.getValue());
        }
       doubanService.saveDoubanBook(resultItems);
    }
}
