package cn.leon.es.service;

import lombok.AllArgsConstructor;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    private CrudRepository crudRepository;
    private RestHighLevelClient restHighLevelClient;



}
