package cn.leon.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageFactory {

    private final ITransactionMessageManagementService transactionMessageManagementService;

    public <T> Message<T> getPayload(T message){
        return MessageBuilder.withPayload(message).build();
    }

    public <T> Message<T> getTxPayload(T message){
        // save msg to db
        transactionMessageManagementService.saveTransactionalMessageRecord();
        return MessageBuilder.withPayload(message).build();
    }
}
