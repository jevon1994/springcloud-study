package cn.leon;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;


@EnableBinding(Sink.class)
@Slf4j
public class Receiver {

    @StreamListener(Sink.INPUT)
    public void receiveMethod(Message<String> message, @Header(AmqpHeaders.CHANNEL) Channel channel,
                              @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) throws Exception {
//        log.debug(message.getPayload());
        throw new RuntimeException("消费失败");
//        channel.basicReject(deliveryTag, false);

    }
}
