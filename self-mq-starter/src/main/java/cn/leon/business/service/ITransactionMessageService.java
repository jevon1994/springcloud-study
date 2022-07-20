package cn.leon.business.service;

import cn.leon.business.enums.DefaultDestination;
import cn.leon.business.model.DefaultTxMessage;

public interface ITransactionMessageService {

    void sendTransactionalMessage(DefaultDestination destination, DefaultTxMessage message);

    void sendTransactionalMessage(DefaultTxMessage message);
}
