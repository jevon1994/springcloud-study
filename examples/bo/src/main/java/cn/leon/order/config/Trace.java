//package cn.leon.order.config;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//import java.math.BigDecimal;
//import java.util.Date;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.atomic.AtomicInteger;
//
//@Data
//@Slf4j
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//public class Trace {
//
//    private final ConcurrentHashMap<String, Integer> CONCURRENT_HASH_MAP = new ConcurrentHashMap<>();
//    private final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);
//    private String traceId;
//    //curr
//    private String spanId;
//    private String parentSpanId;
//    private Date sstime;
//    private Date srtime;
//    private Date cstime;
//    private Date crtime;
//    private String appName;
//
//    public String nextSpanId(String parentSpanId, String currSpanId) {
//        BigDecimal bigDecimal = new BigDecimal(currSpanId);
//        BigDecimal addOne = new BigDecimal("1");
//        BigDecimal nextSpanId = bigDecimal.add(addOne);
//        if (ATOMIC_INTEGER.get() > 0) {
//            double v = ATOMIC_INTEGER.intValue() * 0.1;
//            BigDecimal bigDecimal1 = new BigDecimal(String.valueOf(v));
//            nextSpanId = nextSpanId.add(bigDecimal1);
//        }
//        log.info("currSpanId: {},nextSpanId: {}", currSpanId, nextSpanId);
//        ATOMIC_INTEGER.incrementAndGet();
//        return nextSpanId.toString();
//    }
//}
