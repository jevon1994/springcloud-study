package cn.leon.service;

import cn.leon.CustomBlockHandler;
import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Service;

@Service
public class EchoService {

    @SentinelResource("hello")
    public String hello() {
        return "Hello World Sentinel";
    }

    @SentinelResource(value = "welcome", blockHandler = "urlException", blockHandlerClass = {ExceptionUtil.class})
    public String welcome() throws InterruptedException {
        return "Hello World Sentinel";
    }
    public static class ExceptionUtil {
        public static void urlException(){
            WebCallbackManager.setUrlBlockHandler(new CustomBlockHandler());
        }
        public static String handleException(BlockException ex) {
            return "自定义限流逻辑埋点";
        }

    }
}