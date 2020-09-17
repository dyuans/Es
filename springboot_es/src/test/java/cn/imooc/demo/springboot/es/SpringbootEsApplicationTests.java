package cn.imooc.demo.springboot.es;

import cn.imooc.demo.springboot.es.entity.es.EsBlog;
import cn.imooc.demo.springboot.es.repository.es.EsBlogRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootEsApplicationTests {

    @Autowired
    EsBlogRepository blogRepository;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testEs() {
        Iterable<EsBlog> all = blogRepository.findAll();
        Iterator<EsBlog> iterator = all.iterator();
        EsBlog next = iterator.next();
        System.out.println("---"+next.getTitle());
    }

}
