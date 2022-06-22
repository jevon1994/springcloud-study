package cn.leon.trace.agent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Trace {
    private final ConcurrentHashMap<String,Integer> CONCURRENT_HASH_MAP = new ConcurrentHashMap<>();
    private final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);
    private String traceId;
    //curr
    private String spanId;
    private String parentSpanId;
    private Date sstime;
    private Date srtime;
    private Date cstime;
    private Date crtime;
    private String appName;

    public String nextSpanId(String parentSpanId, String currSpanId) {
        Integer integer = CONCURRENT_HASH_MAP.computeIfAbsent(parentSpanId, val -> 0);
        BigDecimal bigDecimal = new BigDecimal(currSpanId);
        BigDecimal bigDecimal2 = new BigDecimal("1");
        BigDecimal add = bigDecimal.add(bigDecimal2);
        if(integer > 0){
            CONCURRENT_HASH_MAP.put(parentSpanId,integer + 1);
            add = add.add(new BigDecimal("0.1"));
        }
        System.out.println(add);
        return add.toString();
    }
}
