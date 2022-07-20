package cn.leon.business.dao;


import cn.leon.business.model.TransactionalMessageContent;

import java.util.List;

public interface TransactionalMessageContentDao {

    void insert(TransactionalMessageContent record);

    List<TransactionalMessageContent> queryByMessageIds(String messageIds);
}
