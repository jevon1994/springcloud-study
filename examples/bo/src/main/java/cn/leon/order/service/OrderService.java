package cn.leon.order.service;

import cn.leon.order.client.AccountClient;
import cn.leon.order.persistence.Order;
import cn.leon.order.persistence.OrderMapper;
import cn.leon.order.sink.OrderSink;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@EnableBinding({OrderSink.class})
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ObjectMapper objectMapper;

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

    private final OrderSink orderSink;



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
        orderSink.output().sendTransasctionMessage(MessageBuilder.withPayload(message).build());
        log.info("保存订单:{}成功...", orderId);
    }
}
