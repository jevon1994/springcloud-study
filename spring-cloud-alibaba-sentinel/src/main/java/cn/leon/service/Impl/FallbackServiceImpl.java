package cn.leon.service.Impl;

import cn.leon.service.FallbackService;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.stereotype.Service;

@Service
public class FallbackServiceImpl implements FallbackService {


    @Override
    @SentinelResource(value = "getName", fallback = "getNameFallback")
    public String getName(String name) throws InterruptedException {
        for (int i = 0; i < 100000000L; i++) {
            // 异常降级
            throw new RuntimeException();
        }
        // 超时降级
//        Thread.sleep(50);
        return "getName " + name;
    }

    // 该方法降级处理函数，参数要与原函数getName相同，并且返回值类型也要与原函数相同，此外，该方法必须与原函数在同一个类中
    public String getNameFallback(String name){
        return "getNameFallback";
    }
}
