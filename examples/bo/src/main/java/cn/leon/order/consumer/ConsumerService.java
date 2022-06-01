package cn.leon.order.consumer;

import cn.leon.order.sink.Sink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Slf4j
@Service
public class ConsumerService {

    private Sink sink;

    /**
     * 消费分布式事务消息
     */
    @Bean
    public Consumer<String> order() {
        return order -> {
            log.info("接收的普通消息为：{}", order);
        };
    }

    /**
     * 自定义全局异常处理
     *
     * @param message 消息体
     */
    public void error(Message<?> message) {
        ErrorMessage errorMessage = (ErrorMessage) message;
        log.error("Handling ERROR, errorMessage = {} ", errorMessage);
    }

}
