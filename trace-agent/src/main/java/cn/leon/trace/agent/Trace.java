package cn.leon.trace.agent;

import cn.leon.trace.agent.config.ThreadLocalConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Trace {

    private final ConcurrentHashMap<String, Integer> tarceMap = new ConcurrentHashMap<>();

    private String traceId;

    /**
     * curr
     */
    private String spanId;
    private String parentSpanId;

    /**
     * time
     */
    private String sstime;
    @Builder.Default
    private String srtime = ThreadLocalConfig.now();
    private String cstime;
    private String crtime;

    private String appName;

    public synchronized String nextSpanId(String lastParentSpanId, String lastSpanId) {
        /**
         * if exists
         * etc: [0]: lastPid  [1]: lastSid
         * nextSpanId = [lastSid + 1].[count(lastPid)]
         * 0.0,1.1 = > 1.1,2.1/2.2/2.3
         * 1.1,2.2/2.1 => 2.2/2.1,3.1/3.2/3.3
         * 2.2,3.1 => 3.1,4.1/4.2
         */
        Integer integer = tarceMap.computeIfAbsent(lastParentSpanId, val -> 0);
        BigDecimal lastPid = new BigDecimal(lastSpanId);
        BigDecimal parentLevel = lastPid.setScale(0, BigDecimal.ROUND_DOWN).add(BigDecimal.ONE);
        Integer lastPidCount = tarceMap.computeIfPresent(lastParentSpanId, (k, v) -> v + 1);
        final String nextSpan = integer == 0 ? "1" : String.valueOf(lastPidCount);
        final String nextSpanId = parentLevel.toString().concat(".").concat(nextSpan);
        log.info("lastParentSpanId: {},lastSpanId: {},nextSpanId: {}", lastParentSpanId, lastSpanId, nextSpanId);
        return nextSpanId;
    }
}
