package cn.leon.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StreamRabbitTemplate implements StreamTemplate {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void send(String content) {
        rabbitTemplate.convertAndSend(content);
    }

    @Override
    public <T> T consume(String queuename, long timeout) {
        return rabbitTemplate.receiveAndConvert(queuename, timeout, new ParameterizedTypeReference<T>() {
        });
    }

    @Override
    public <T> T poll(String queuename) {
        return rabbitTemplate.receiveAndConvert(queuename, new ParameterizedTypeReference<T>() {
        });
    }

}
