package cn.leon;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Value(value = "${spring.datasource.driver-class-name}")
    private String className;

    @GetMapping
    public String getConfig(){
        return className;
    }
}
