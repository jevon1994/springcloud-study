package cn.leon.business.enums;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
@Builder
public class DefaultDestination {

    private ExchangeType exchangeType;

    private String queueName;

    @Value("${spring.application.name}")
    private String exchangeName;


    private String routingKey;
}
