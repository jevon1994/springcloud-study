package cn.leon.order.service;

public interface StreamTemplate {


    void send(String content);

    abstract <T> T consume(String queuename, long timeout);

    <T> T poll(String queuename);
}
