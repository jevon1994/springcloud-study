package cn.leon.trace.agent;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Configuration
public class ThreadLocalConfig {

    // 链路参数
    public static final String TRACE_ID = "wj-trace-id";
    public static final String SPAN_ID = "wj-trace-spanid";
    public static final String PARENT_SPAN_ID = "wj-trace-parentspanid";
    public static final String SERVER_RECEIVE_TIME = "wj-trace-srtime";
    public static final String CLIENT_RECEIVE_TIME = "wj-trace-crtime";
    public static final String SERVER_SEND_TIME = "wj-trace-sstime";
    public static final String CLIENT_SEND_TIME = "wj-trace-cstime";
    public static final String APP_NAME = "wj-app-name";

    public static final String DEFAULT_SPAN_ID = "1.1";
    public static final String DEFAULT_PARENT_SPAN_ID = "0.0";

    // todo question
    private static final TransmittableThreadLocal<Trace> TRANSMITTABLE_THREAD_LOCAL = new TransmittableThreadLocal<>();

    public static final TransmittableThreadLocal<Trace> getTransmittableThreadLocal() {
        return TRANSMITTABLE_THREAD_LOCAL;
    }
}
