package cn.leon.order.persistence;

import lombok.Data;

@Data
public class TransactionLog extends BaseEntity {
    private String business;
    private String pkId;
}
