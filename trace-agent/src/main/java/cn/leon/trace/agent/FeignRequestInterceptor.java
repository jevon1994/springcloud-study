package cn.leon.trace.agent;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
public class FeignRequestInterceptor implements RequestInterceptor {

    private String appname;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        Trace trace = ThreadLocalConfig.getTransmittableThreadLocal().get();
        if (trace != null) {
            String traceId = trace.getTraceId();
            String spanId = trace.getSpanId();

            requestTemplate.header(ThreadLocalConfig.TRACE_ID, traceId);
            requestTemplate.header(ThreadLocalConfig.SPAN_ID, trace.nextSpanId(spanId));
            requestTemplate.header(ThreadLocalConfig.PARENT_SPAN_ID, spanId);
            requestTemplate.header(ThreadLocalConfig.APP_NAME, trace.getAppName());
        } else {
            requestTemplate.header(ThreadLocalConfig.TRACE_ID, UUID.randomUUID().toString());
            requestTemplate.header(ThreadLocalConfig.APP_NAME, appname);
            requestTemplate.header(ThreadLocalConfig.SPAN_ID, ThreadLocalConfig.DEFAULT_SPAN_ID);
            requestTemplate.header(ThreadLocalConfig.PARENT_SPAN_ID, ThreadLocalConfig.DEFAULT_PARENT_SPAN_ID);
        }
        log.info("appname: {},headers: {}", appname, requestTemplate.headers());
    }
}
