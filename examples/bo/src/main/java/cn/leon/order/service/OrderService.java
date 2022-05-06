package cn.leon.order.service;

import cn.leon.order.client.AccountClient;
import cn.leon.order.persistence.Order;
import cn.leon.order.persistence.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderService {

    @Autowired
    private AccountClient accountClient;
    @Autowired
    private OrderMapper orderMapper;

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
}
