package cn.leon.es.service;

import cn.leon.es.EsApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = EsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SearchServiceImplTest {

    @Autowired
    private SearchService searchService;

    public static final String id = "79b689c0-0e88-4301-8925-eabc24c87cab";

    @Test
    void findById() {
        System.out.println(searchService.findById(id));
    }

    @Test
    void save() {
        searchService.save();
    }

    @Test
    void saveAll() {
        searchService.saveAll();
    }

    @Test
    void deleteById() {
        searchService.deleteById(id);
    }

    @Test
    void deleteAll() {
        searchService.deleteAll();
    }

    @Test
    void search() {
        searchService.search("type", "doc");
    }
}