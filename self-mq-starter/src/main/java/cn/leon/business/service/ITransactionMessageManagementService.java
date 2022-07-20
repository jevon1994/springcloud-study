package cn.leon.business.service;

import cn.leon.business.model.TransactionalMessage;

public interface ITransactionMessageManagementService {

    void saveTransactionalMessageRecord(TransactionalMessage record, String content);

    void sendMessageSync(TransactionalMessage record, String content);

    void processPendingCompensationRecords();
}
