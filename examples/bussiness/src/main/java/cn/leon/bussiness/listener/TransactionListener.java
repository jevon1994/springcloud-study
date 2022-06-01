package cn.leon.bussiness.listener;

import cn.leon.bussiness.Order;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RocketMQTransactionListener(txProducerGroup = "order-group")
public class TransactionListener implements RocketMQLocalTransactionListener {


    //本地事务
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        MessageHeaders headers = message.getHeaders();
        String transactionId = String.valueOf(headers.get(RocketMQHeaders.TRANSACTION_ID));
        Long shareId = Long.valueOf(String.valueOf(headers.get("share_id")));
        Order order = null;
        try {
            order = JSONObject.parseObject(new String((byte[]) message.getPayload()), Order.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Long args = (Long) o;
        log.info(String.format("half message\npayload:%s, arg:%s, transactionId:%s", order, args, message.getHeaders().get(RocketMQHeaders.TRANSACTION_ID)));
        // save @Transactional
//        if (success) {
//            return RocketMQLocalTransactionState.COMMIT;
//        } else {
//            return RocketMQLocalTransactionState.ROLLBACK;
//        }
        return RocketMQLocalTransactionState.COMMIT;
    }

    /**
     * 回查
     *
     * @param message
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        Order order = JSON.parseObject(new String((byte[]) message.getPayload()), Order.class);
        // 业务查询本地事务是否执行成功
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        // 根据message去查询本地事务是否执行成功，如果成功，则commit
        if (orders.size() > 0) {
            return RocketMQLocalTransactionState.COMMIT;
        } else {
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }
}
