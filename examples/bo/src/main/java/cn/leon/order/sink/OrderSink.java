package cn.leon.order.sink;

import cn.leon.business.config.SelfTxChannel;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.SubscribableChannel;

public interface OrderSink {
    String ORDER_IN_0 = "order-in-0";
    String ORDER_OUT_0 = "order-out-0";

    @Output(ORDER_OUT_0)
    SelfTxChannel output();

    @Input(ORDER_IN_0)
    SubscribableChannel input();
}
