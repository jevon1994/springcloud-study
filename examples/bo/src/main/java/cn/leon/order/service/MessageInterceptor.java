package cn.leon.order.service;

import cn.leon.order.enums.ExchangeType;
import cn.leon.order.persistence.TransactionalMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.config.GlobalChannelInterceptor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
@RequiredArgsConstructor
@GlobalChannelInterceptor
public class MessageInterceptor implements ChannelInterceptor {

    private final ITransactionMessageManagementService managementService;

    private Message<?> message1;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        MessageHeaders headers = message.getHeaders();
        Object o = headers.get("tx");
        if (Boolean.TRUE.equals(o)) {
            System.out.println("发送消息之前!!!!!!1");
            //save

            TransactionalMessage record = new TransactionalMessage();
            record.setBusinessKey("xxxxxx");
            record.setBusinessModule("SAVE_ORDER");
            record.setExchangeType(ExchangeType.DIRECT.getType());
            record.setExchangeName("tm.test.exchange");
            record.setQueueName("tm.test.queue");
            record.setRoutingKey("tm.test.key");
            String content = message.getPayload().toString();
            managementService.saveTransactionalMessageRecord(record, content);
            //register
            System.out.println(TransactionSynchronizationManager.isActualTransactionActive());
            // 注册事务同步器
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    send(message, channel);
                }
            });
            return message1;
        }
        return message;
    }

    private Message<?> send(Message<?> message, MessageChannel channel) {
        Message<?> message2 = ChannelInterceptor.super.preSend(message, channel);
        message1 = message2;
        return message2;
    }
}
