package com.hnayyc.giftcrawler.pipeline;

import com.hnayyc.giftcrawler.service.DoubanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Controller
public class DbPipeline implements Pipeline {

    @Autowired
    DoubanService doubanService;

    @Override
    public void process(ResultItems resultItems, Task task) {
        String value = resultItems.get("key");

    }
}
