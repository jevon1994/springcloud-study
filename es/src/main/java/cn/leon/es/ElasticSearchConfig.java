package cn.leon.es;

import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.config.ElasticsearchConfigurationSupport;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "cn.leon.es.repo")
@ComponentScan(basePackages = {"cn.leon.es"})
public class ElasticSearchConfig extends ElasticsearchConfigurationSupport {

    @Bean
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration config = ClientConfiguration
                .builder()
                .connectedTo("172.16.255.143:30920")
                .build();
//        Settings settings = Settings.builder()
//                .put()
//                .build();
//        TransportClient transportClient = new PreBuiltTransportClient(settings);

        return RestClients.create(config).rest();
    }

    @Bean
    public ElasticsearchRestTemplate elasticsearchRestTemplate() {
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }

    @Bean(name = {"elasticsearchOperations", "elasticsearchTemplate"})
    public ElasticsearchOperations elasticsearchOperations() {
        return new ElasticsearchRestTemplate(elasticsearchClient(), elasticsearchConverter(), resultsMapper());
    }
}
