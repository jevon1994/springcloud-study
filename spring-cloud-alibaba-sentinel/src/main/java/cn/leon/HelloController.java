package cn.leon;

import cn.leon.service.EchoService;
import cn.leon.service.FallbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
public class HelloController {

    @Resource
    private EchoService helloService;
    @Autowired
    private FallbackService fallbackService;
    @RequestMapping("/hello")
    public String hello() {
        return helloService.hello();
    }

    @RequestMapping("/welcome")
    public String welcome() throws InterruptedException {
        return helloService.welcome();
    }
    @RequestMapping("/getName")
    public String getName() throws InterruptedException {
        return fallbackService.getName("Leon");
    }

}
