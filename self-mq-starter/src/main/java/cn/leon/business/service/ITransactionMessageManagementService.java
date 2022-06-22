package cn.leon.mq.service;

import cn.leon.mq.model.TransactionalMessage;

public interface ITransactionMessageManagementService {

    void saveTransactionalMessageRecord(TransactionalMessage record, String content);

    void sendMessageSync(TransactionalMessage record, String content);

    void processPendingCompensationRecords();
}
