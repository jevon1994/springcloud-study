//package cn.leon.order.config;
//
//import com.alibaba.ttl.TransmittableThreadLocal;
//
//import java.util.UUID;
//
//public class ThreadLocalConfig {
//
//    // 链路参数
//    public static final String TRACE_ID = "wj-trace-id";
//    public static final String SPAN_ID = "wj-trace-spanid";
//    public static final String PARENT_SPAN_ID = "wj-trace-parentspanid";
//    public static final String SERVER_RECEIVE_TIME = "wj-trace-srtime";
//    public static final String CLIENT_RECEIVE_TIME = "wj-trace-crtime";
//    public static final String SERVER_SEND_TIME = "wj-trace-sstime";
//    public static final String CLIENT_SEND_TIME = "wj-trace-cstime";
//    public static final String APP_NAME = "wj-app-name";
//
//    public static final String DEFAULT_SPAN_ID = "1.1";
//    public static final String DEFAULT_PARENT_SPAN_ID = "0.0";
//    // 初始化一个TransmittableThreadLocal
//    private static TransmittableThreadLocal<Trace> TRANSMITTABLE_THREAD_LOCAL = new TransmittableThreadLocal<>();
//
//    public static TransmittableThreadLocal<Trace> getTransmittableThreadLocal() {
//        return TRANSMITTABLE_THREAD_LOCAL;
//    }
//
//
//    public static Trace getDefaultTrace(String appName) {
//        return Trace.builder()
//                .traceId(UUID.randomUUID().toString())
//                .parentSpanId(DEFAULT_PARENT_SPAN_ID)
//                .spanId(DEFAULT_SPAN_ID)
//                .appName(appName)
//                .build();
//    }
//
//    public static Trace getCurrentTrace(String appName, String traceId, String currParentSpanId, String currSpanId) {
//        return Trace.builder()
//                .traceId(traceId)
//                .parentSpanId(currParentSpanId)
//                .spanId(currSpanId)
//                .appName(appName)
//                .build();
//    }
//}
