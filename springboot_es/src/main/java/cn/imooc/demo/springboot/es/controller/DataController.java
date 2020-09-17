package cn.imooc.demo.springboot.es.controller;


import cn.imooc.demo.springboot.es.entity.es.EsBlog;
import cn.imooc.demo.springboot.es.entity.mysql.MysqlBlog;
import cn.imooc.demo.springboot.es.repository.es.EsBlogRepository;
import cn.imooc.demo.springboot.es.repository.mysql.MysqlBlogRepository;
import lombok.Data;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class DataController {
    @Autowired
    MysqlBlogRepository mysqlBlogRepository;

    @Autowired
    EsBlogRepository esBlogRepository;

    @GetMapping("/blogs")
    public Object blog(){
        List<MysqlBlog> mysqlBlogs = mysqlBlogRepository.queryAll();
        return mysqlBlogs;
    }

    @PostMapping("/search")
    public Object search(@RequestBody Param param){
        HashMap<String, Object> map = new HashMap<>();

        StopWatch watch = new StopWatch();
        watch.start();

        String type = param.getType();
        String keywords = param.getKeyword();
        if (type.equalsIgnoreCase("mysql")){
            System.out.print("I am here");
            System.out.println("keywords:"+keywords);
            List<MysqlBlog> mysqlBlogs = mysqlBlogRepository.queryBlogs(param.getKeyword());
            map.put("list", mysqlBlogs);
        }else if (type.equalsIgnoreCase("es")){
            //    POST /mysqles/_search
////    {
////        "query": {
////        "bool": {
////            "should": [
////            {
////                "match_phrase": {
////                "title": "springboot"
////            }
////            },
////            {
////                "match_phrase": {
////                "contetn": "springboot"
////            }
////            }
////      ]
////        }
////
////    }
////    }

            BoolQueryBuilder builder = QueryBuilders.boolQuery();
            builder.should(QueryBuilders.matchPhraseQuery("title", param.getKeyword()));
            builder.should(QueryBuilders.matchPhraseQuery("content", param.getKeyword()));
            String s = builder.toString();
            System.out.print(s);
//            Iterable<EsBlog> search =  esBlogRepository.search(builder);
            Page<EsBlog> search =  (Page<EsBlog>) esBlogRepository.search(builder);
            List<EsBlog> content = search.getContent();
            map.put("list", content);

        }else {
            return "I don't understand";
        }

        watch.stop();
        long totalTimeMills =  watch.getTotalTimeMillis();
        map.put("duration", totalTimeMills);
        System.out.print(map);
        return  map;
    }

    @Data
    public static  class Param{
//        type = mysql or es
        private String type;

        private String keyword;

    }

    @GetMapping("/blog/{id}")
    public Object blog(@PathVariable("id") Integer id){
        Optional<MysqlBlog> byId =  mysqlBlogRepository.findById(id);
        MysqlBlog blog = byId.get();
        return blog;
    }
}
