package cn.leon.es.service;

import cn.leon.es.LogEntity;
import cn.leon.es.repo.LogRepository;
import lombok.AllArgsConstructor;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SearchServiceImpl implements SearchService {

    private LogRepository logRepository;

    private RestClient restClient;

    @Override
    public LogEntity findById(String id) {
        return logRepository.findById(id).get();
    }

    @Override
    public void save() {
        LogEntity logEntity = getLogEntity();
        logRepository.save(logEntity);
    }

    private LogEntity getLogEntity() {
        return LogEntity.builder()
                .type("doc")
                .id(UUID.randomUUID().toString())
                .server("001" + Math.random())
                .url("www.baidu.com" + Math.random())
                .build();
    }

    @Override
    public void saveAll() {
        List<LogEntity> objects = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            LogEntity logEntity = getLogEntity();
            objects.add(logEntity);
        }
        logRepository.saveAll(objects);
    }

    @Override
    public void deleteById(String id) {
        logRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        logRepository.deleteAll();
    }

    @Override
    public void search(String key, String val) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery(key, val);
        boolQueryBuilder.must(queryBuilder);
        Iterable<LogEntity> search = logRepository.search(boolQueryBuilder);
        search.forEach(System.out::println);
    }
}
