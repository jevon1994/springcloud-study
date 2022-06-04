package cn.leon.mq.service.impl;

import cn.leon.mq.enums.DefaultDestination;
import cn.leon.mq.enums.ExchangeType;
import cn.leon.mq.model.DefaultTxMessage;
import cn.leon.mq.model.TransactionalMessage;
import cn.leon.mq.service.ITransactionMessageManagementService;
import cn.leon.mq.service.ITransactionMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RequiredArgsConstructor
public class TransactionMessageServiceImpl implements ITransactionMessageService {

    private final AmqpAdmin amqpAdmin;
    private final ITransactionMessageManagementService managementService;
    private static final ConcurrentMap<String, Boolean> QUEUE_ALREADY_DECLARE = new ConcurrentHashMap<>();

    @Override
    public void sendTransactionalMessage(DefaultDestination destination, DefaultTxMessage message) {
        String queueName = destination.getQueueName();
        String exchangeName = destination.getExchangeName();
        String routingKey = destination.getRoutingKey();
        ExchangeType exchangeType = destination.getExchangeType();
        QUEUE_ALREADY_DECLARE.computeIfAbsent(queueName, k -> {
            Queue queue = new Queue(queueName);
            amqpAdmin.declareQueue(queue);
            Exchange exchange = new CustomExchange(exchangeName, exchangeType.getType());
            amqpAdmin.declareExchange(exchange);
            Binding binding = BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
            amqpAdmin.declareBinding(binding);
            return true;
        });
        sendTransactionalMessage(message);

    }

    @Override
    public void sendTransactionalMessage(DefaultTxMessage message) {
        TransactionalMessage record = new TransactionalMessage();
//        record.setQueueName(queueName);
//        record.setExchangeName(exchangeName);
//        record.setExchangeType(exchangeType.getType());
//        record.setRoutingKey(routingKey);
        record.setBusinessModule(message.getBusinessModule());
        record.setBusinessKey(message.getBusinessKey());
        String content = message.getContent();
        // save record
        managementService.saveTransactionalMessageRecord(record, content);
        System.out.println(TransactionSynchronizationManager.isActualTransactionActive());
        // 注册事务同步器
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                managementService.sendMessageSync(record, content);
            }
        });
    }
}
