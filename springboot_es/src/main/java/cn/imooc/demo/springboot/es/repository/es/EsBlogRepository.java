package cn.imooc.demo.springboot.es.repository.es;

import cn.imooc.demo.springboot.es.entity.es.EsBlog;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsBlogRepository extends ElasticsearchRepository<EsBlog, Integer> {

}
