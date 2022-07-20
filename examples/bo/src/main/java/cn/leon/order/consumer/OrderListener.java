package cn.leon.order.consumer;

import cn.leon.order.sink.AccountSink;
import cn.leon.order.sink.OrderSink;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@EnableBinding({OrderSink.class})
public class OrderListener {

    @StreamListener(OrderSink.ORDER_IN_0)
    public void onMessage(Message<String> message, @Header(AmqpHeaders.CHANNEL) Channel channel,
                          @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag){
        System.out.println(message);
    }
}
