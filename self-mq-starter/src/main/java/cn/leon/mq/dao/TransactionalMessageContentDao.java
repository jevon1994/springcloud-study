package cn.leon.order.dao;

import cn.leon.order.persistence.TransactionalMessageContent;

import java.util.List;

public interface TransactionalMessageContentDao {

    void insert(TransactionalMessageContent record);

    List<TransactionalMessageContent> queryByMessageIds(String messageIds);
}
