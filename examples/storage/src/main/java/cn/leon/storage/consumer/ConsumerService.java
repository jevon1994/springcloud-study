package cn.leon.storage.consumer;

import cn.leon.storage.Order;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@Service
public class ConsumerService{

    private AtomicInteger atomicInteger = new AtomicInteger(0);
    private AtomicInteger atomicInteger2 = new AtomicInteger(0);

    /**
     * 消费分布式事务消息
     */
    @Bean
    public Consumer<Message<String>> order() {
        return order -> {
            int andIncrement = atomicInteger.getAndIncrement();
            log.info("第 {} 次接收的普通消息为：{}", andIncrement, order);
            MessageHeaders headers = order.getHeaders();
            System.out.println(headers);
            int i = 10 / 0;
        };
    }
//
//    @Bean
//    public Consumer<String> order2() {
//        return order -> {
//            int andIncrement = atomicInteger2.getAndIncrement();
//            log.info("第 {} 次接收的普通消息为：{}", andIncrement, order);
//            int i = 10 / 0;
//        };
//    }

//    @Bean
//    public Function<Flux<String>, Mono<Void>> consumerEvent() {
//        return flux -> flux.map(message -> {
//            System.out.println(message);
//            return message;
//        }).then();
//    }
//
//    // 第二种方式
//// 注意使用Flux 要调用 subscribe 不然这个方法不会被消费
//    @Bean
//    public Consumer<Flux<String>> consumerEvent2() {
//        return flux -> flux.map(message -> {
//            System.out.println(message);
//            return message;
//        }).subscribe();
//    }
//
//    @Bean
//    public Consumer<String> consumerEvent3() {
//        return message -> System.out.println(message);
//    }

    /**
     * 自定义全局异常处理
     *
     * @param message 消息体
     */
    public void error(Message<?> message) {
        ErrorMessage errorMessage = (ErrorMessage) message;
        log.error("Handling ERROR, errorMessage = {} ", errorMessage);
    }


    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        log.info("消费者线程监听到消息。");
        try {
            for (MessageExt message : list) {
                log.info("开始处理订单数据，准备增加积分....");
                Order order = JSONObject.parseObject(message.getBody(), Order.class);
                log.info(order.toString());
                int i = 10 / 0;
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        } catch (Exception e) {
            log.error("处理消费者数据发生异常。{}", e);
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
    }
}
