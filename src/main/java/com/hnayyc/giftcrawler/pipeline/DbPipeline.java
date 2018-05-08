package com.hnayyc.giftcrawler.pipeline;

import com.hnayyc.giftcrawler.service.DoubanService;
import com.hnayyc.giftcrawler.utils.JdbcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.sql.Connection;
import java.util.Map;

@Controller
public class DbPipeline implements Pipeline {

    @Autowired
    DoubanService doubanService;

    @Override
    public void process(ResultItems resultItems, Task task) {
//        String code = resultItems.get("code");
//        String name = resultItems.get("name");
//        String sql = "insert into coffee_dict(dict_code, dict_name) values('" + code + "','" + name + "')";
//        JdbcUtils db = new JdbcUtils();
//        db.excuteSql(sql);

        System.out.println("DbPipeline : " + resultItems.getRequest().getUrl());
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            System.out.println( "DbPipeline : " + entry.getKey() + ":\t" + entry.getValue());
        }
    }
}
