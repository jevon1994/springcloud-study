package cn.leon.mq.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseEntity {
    private Long id;
    private LocalDateTime createTime;
    private LocalDateTime editTime;
    private String creator;
    private String editor;
    private Integer deleted;
    private Integer versionId;
}
