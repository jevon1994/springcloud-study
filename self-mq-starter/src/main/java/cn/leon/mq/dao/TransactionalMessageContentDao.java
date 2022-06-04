package cn.leon.mq.dao;


import cn.leon.mq.model.TransactionalMessageContent;

import java.util.List;

public interface TransactionalMessageContentDao {

    void insert(TransactionalMessageContent record);

    List<TransactionalMessageContent> queryByMessageIds(String messageIds);
}
