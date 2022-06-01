package cn.leon.order.enums;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DefaultDestination {
    private ExchangeType exchangeType;
    private String queueName;
    private String exchangeName;
    private String routingKey;
}
