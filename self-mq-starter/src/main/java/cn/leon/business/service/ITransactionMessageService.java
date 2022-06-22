package cn.leon.mq.service;

import cn.leon.mq.enums.DefaultDestination;
import cn.leon.mq.model.DefaultTxMessage;

public interface ITransactionMessageService {

    void sendTransactionalMessage(DefaultDestination destination, DefaultTxMessage message);

    void sendTransactionalMessage(DefaultTxMessage message);
}
