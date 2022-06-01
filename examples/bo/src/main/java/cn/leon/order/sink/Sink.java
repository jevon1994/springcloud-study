package cn.leon.order.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Sink {
    String ORDER_IN_0 = "order-in-0";
    String ORDER_OUT_0 = "order-out-0";

    @Output(ORDER_OUT_0)
    MessageChannel output();

    @Input(ORDER_IN_0)
    SubscribableChannel input();
}
