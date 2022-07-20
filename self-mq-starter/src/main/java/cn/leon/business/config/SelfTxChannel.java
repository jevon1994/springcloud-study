package cn.leon.business.config;

import org.springframework.messaging.MessageChannel;

public interface SelfTxChannel extends MessageChannel {

    <T> void sendTransasctionMessage(T content);
}
