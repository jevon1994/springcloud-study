package cn.leon.order.service;


import cn.leon.order.enums.DefaultDestination;
import cn.leon.order.persistence.DefaultTxMessage;

public interface ITransactionMessageService {

    void sendTransactionalMessage(DefaultDestination destination, DefaultTxMessage message);

    void sendTransactionalMessage(DefaultTxMessage message);
}
