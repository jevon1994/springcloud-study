package cn.leon.business.model;

import lombok.Data;

@Data
public class TransactionLog extends BaseEntity {
    private String business;
    private String pkId;
}
