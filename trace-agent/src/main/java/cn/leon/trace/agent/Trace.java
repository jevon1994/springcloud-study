package cn.leon.trace.agent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Trace {

    @Builder.Default
    private AtomicInteger counter = new AtomicInteger(0);

    private String traceId;
    //curr
    private String spanId;
    private String parentSpanId;
    private Date sstime;
    private Date srtime;
    private Date cstime;
    private Date crtime;
    private String appName;

    public synchronized String nextSpanId(String currSpanId) {
        BigDecimal bigDecimal = new BigDecimal(currSpanId);
        BigDecimal addOne = new BigDecimal("1");
        BigDecimal nextSpanId = bigDecimal.add(addOne);
        if (counter.get() > 0) {
            double v = counter.intValue() * 0.1;
            final BigDecimal bigDecimal1 = new BigDecimal(String.valueOf(v));
            nextSpanId = nextSpanId.add(bigDecimal1);
        }
        System.out.println(counter.get());
        log.info("currSpanId: {},nextSpanId: {}", currSpanId, nextSpanId);
        counter.incrementAndGet();
        return nextSpanId.toString();
    }
}
