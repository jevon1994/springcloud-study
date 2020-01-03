package cn.leon;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @Author mujian
 * @Description 自定义接口收发消息
 * @Date 16:55 2019-3-26 0026
 */
public interface Sink {

    String OUTPUT = "output"; // 输出通道名称

    String INPUT = "input"; // 输入通道名称

    @Input(INPUT)
    SubscribableChannel input();

    @Output(OUTPUT)
    MessageChannel output();
}
