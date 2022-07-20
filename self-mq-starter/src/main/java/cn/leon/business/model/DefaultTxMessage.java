package cn.leon.business.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DefaultTxMessage {
    private String businessModule;
    private String businessKey;
    private String content;
}
