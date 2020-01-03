package cn.leon.service;

import org.springframework.stereotype.Component;

@Component
public class NoFoundService implements HelloService {

    @Override
    public String hello(String name) {
        return "sorry " + name;
    }
}
