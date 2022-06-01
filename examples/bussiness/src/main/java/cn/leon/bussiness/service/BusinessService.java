package cn.leon.bussiness.service;

import cn.leon.bussiness.Order;
import cn.leon.bussiness.client.OrderClient;
import cn.leon.bussiness.client.StorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class BusinessService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessService.class);

    @Autowired
    private StorageClient storageClient;
    @Autowired
    private OrderClient orderClient;

    @Autowired
    private StreamBridge streamBridge;

    @Autowired
    private TransactionService transactionService;

    /**
     * 减库存，下订单
     *
     * @param userId
     * @param commodityCode
     * @param orderCount
     */
//    @GlobalTransactional
    public void purchase(String userId, String commodityCode, int orderCount) {
        try {
            orderClient.create(100,userId, commodityCode, new Integer(orderCount));
        }catch (Exception e){
            log.error("ex{}",e);
        }




        System.out.println("save order");
    }


    public void purchaseStream(String userId, String commodityCode, int orderCount) {
        Order order = Order.builder().count(orderCount)
                .commodityCode(commodityCode)
                .userId(userId)
                .build();
        //send msg
        String transactionId = UUID.randomUUID().toString();
        Message<Order> message = MessageBuilder.withPayload(order)
                .setHeader(RocketMQHeaders.TRANSACTION_ID, transactionId)
                .setHeader(MessageConst.PROPERTY_TAGS, "ordertag")
                .setHeader("share_id", 3).build();
        transactionService.sendStream(message);
    }

    @Transactional
    public void createOrder(String txId){

        //1 save order

        //2 save tx log

    }

    public void createOrder(){

        // todo: send msg
    }
}
