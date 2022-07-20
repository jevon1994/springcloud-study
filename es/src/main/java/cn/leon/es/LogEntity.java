package cn.leon.es;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = ".test",type = "doc")
public class LogEntity {

    @Id
    private String id;
    private String type;
    private String config;
    private String dashboard;
    private String graphWorkspace;
    private String indexPattern;
    private String search;
    private String server;
    private String timelionSheet;
    private DateTime updatedAt;
    private String url;
    private Visualization visualization;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Visualization{
        private String description;
        private KibanaSavedObjectMeta kibanaSavedObjectMeta;
        private String savedSearchId;
        private String title;
        private String uiStateJSON;
        private Integer version;
        private String visState;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class KibanaSavedObjectMeta{
        private String searchSourceJSON;
    }

}
