package cn.leon.bussiness.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class TransactionService {

    private final RocketMQTemplate rocketMQTemplate;

    private final StreamBridge streamBridge;

    public void send(Message message) {
        try {
            // 发送事务消息
            TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction("order-group", "order-topic", message, 4L);
            if(SendStatus.SEND_OK.equals(transactionSendResult.getSendStatus())){
               return;
            }
        } catch (Exception e) {
            // 消息发送异常，按下单失败处理
            log.error("下单成功, 消息发送失败");
            throw new RuntimeException("下单成功, 消息发送失败");
        }
        throw new RuntimeException("消息发送未成功");
    }


    public void sendStream(Message message) {
        try {
            boolean send_ok = streamBridge.send("order-out-0", message);
            if (send_ok) {
                return;
            }
        } catch (Exception e) {
            throw new RuntimeException("下单成功, 发消息异常: ", e.getCause());
        }
        throw new RuntimeException("消息未发送成功");

    }
}
