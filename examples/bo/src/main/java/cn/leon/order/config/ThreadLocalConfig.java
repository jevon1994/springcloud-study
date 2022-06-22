package cn.leon.trace.agent;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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

    public static final String DEFAULT_SPAN_ID = "1";
    public static final String DEFAULT_PARENT_SPAN_ID = "0";
    // 初始化一个TransmittableThreadLocal
    private static TransmittableThreadLocal<TRACE> TRANSMITTABLE_THREAD_LOCAL = new TransmittableThreadLocal<>();

    public static TransmittableThreadLocal<TRACE> getTransmittableThreadLocal(){
        return TRANSMITTABLE_THREAD_LOCAL;
    }

    // 信息封装类
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TRACE{
        private String traceId;
        private String spanId;
        private String parentSpanId;
        private Date sstime;
        private Date srtime;
        private Date cstime;
        private Date crtime;
        private String appName;
    }
}
