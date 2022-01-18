package cn.leon.kubernetes.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("config")
public class ConfigClientController {

    @Value("${spring.r2dbc.url:}")
    private String url;

    @GetMapping("url")
    public String getConfig() {
        return url;
    }

}
