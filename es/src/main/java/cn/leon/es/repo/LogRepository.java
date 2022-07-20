package cn.leon.es.repo;

import cn.leon.es.LogEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface LogRepository extends ElasticsearchRepository<LogEntity,String> {
}
