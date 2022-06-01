package cn.leon.order.service;

import cn.leon.order.client.AccountClient;
import cn.leon.order.persistence.Order;
import cn.leon.order.persistence.OrderMapper;
import cn.leon.order.sink.Sink;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private OrderMapper orderMapper;

    private final Sink sink;

    private final MessageFactory messageFactory;

    private final ObjectMapper objectMapper;

    public void create(String userId, String commodityCode, Integer count) {
        BigDecimal orderMoney = new BigDecimal(count).multiply(new BigDecimal(5));
        Order order = new Order();
        order.setUserId(userId);
        order.setCommodityCode(commodityCode);
        order.setCount(count);
        order.setMoney(orderMoney);

        orderMapper.insert(order);

        accountClient.debit(userId, orderMoney);

    }


    @Transactional(rollbackFor = Exception.class)
    public void saveOrder() throws JsonProcessingException {
        String orderId = UUID.randomUUID().toString();
        BigDecimal amount = BigDecimal.valueOf(100L);
        Map<String, Object> message = new HashMap<>();
        Order order = new Order();
        order.setUserId(orderId);
        order.setCommodityCode("xxxx001");
        order.setCount((int) Math.random());
        order.setMoney(amount);
        orderMapper.insert(order);
        message.put("order", order);
        String content = objectMapper.writeValueAsString(message);
        Message<String> txPayload = messageFactory.getTxPayload(content);
        sink.output().send(txPayload);
//        transactionMessageS. ervice.sendTransactionalMessage(
//                DefaultDestination.builder()
//                        .exchangeName("tm.test.exchange")
//                        .queueName("tm.test.queue")
//                        .routingKey("tm.test.key")
//                        .exchangeType(ExchangeType.DIRECT)
//                        .build(),
//                DefaultTxMessage.builder()
//                        .businessKey(orderId)
//                        .businessModule("SAVE_ORDER")
//                        .content(content)
//                        .build()
//        );
        log.info("保存订单:{}成功...", orderId);
    }
}
