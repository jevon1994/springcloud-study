package cn.leon.es;

import lombok.AllArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DocumentService {

    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public void search(){
    }

}
