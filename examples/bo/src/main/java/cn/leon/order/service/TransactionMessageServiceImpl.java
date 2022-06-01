package cn.leon.order.service;

import cn.leon.order.enums.DefaultDestination;
import cn.leon.order.enums.ExchangeType;
import cn.leon.order.persistence.DefaultTxMessage;
import cn.leon.order.persistence.TransactionalMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
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
        TransactionalMessage record = new TransactionalMessage();
        record.setQueueName(queueName);
        record.setExchangeName(exchangeName);
        record.setExchangeType(exchangeType.getType());
        record.setRoutingKey(routingKey);
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
