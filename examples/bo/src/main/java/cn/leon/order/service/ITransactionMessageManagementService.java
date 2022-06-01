package cn.leon.order.service;


import cn.leon.order.persistence.TransactionalMessage;

public interface ITransactionMessageManagementService {

    void saveTransactionalMessageRecord(TransactionalMessage record, String content);

    void sendMessageSync(TransactionalMessage record, String content);

    void processPendingCompensationRecords();
}
