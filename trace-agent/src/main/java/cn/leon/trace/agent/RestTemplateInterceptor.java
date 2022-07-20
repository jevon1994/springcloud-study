package cn.leon.trace.agent;

import cn.leon.trace.agent.config.ThreadLocalConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

    private String appName;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        Trace trace = ThreadLocalConfig.getTransmittableThreadLocal().get();
        addRequestHeader(request,trace);
        trace.setCstime(ThreadLocalConfig.now());
        ClientHttpResponse response = execution.execute(request, body);
        trace.setCrtime(ThreadLocalConfig.now());
        log.info("traceId: {},parentspanId: {},spanId: {},cstime: {},crtime: {}", trace.getTraceId(), trace.getParentSpanId(), trace.getSpanId(), trace.getCstime(), trace.getCrtime());
        return response;
    }

    private void addRequestHeader(HttpRequest request,Trace trace) {
        HttpHeaders headers = request.getHeaders();

        if (trace != null) {
            String traceId = trace.getTraceId();
            String spanId = trace.getSpanId();

            headers.add(ThreadLocalConfig.TRACE_ID, traceId);
            headers.add(ThreadLocalConfig.SPAN_ID, trace.nextSpanId(trace.getParentSpanId(),spanId));
            headers.add(ThreadLocalConfig.PARENT_SPAN_ID, spanId);
            headers.add(ThreadLocalConfig.APP_NAME, trace.getAppName());
        } else {
            headers.add(ThreadLocalConfig.TRACE_ID, UUID.randomUUID().toString());
            headers.add(ThreadLocalConfig.APP_NAME, appName);
            headers.add(ThreadLocalConfig.SPAN_ID, ThreadLocalConfig.DEFAULT_SPAN_ID);
            headers.add(ThreadLocalConfig.PARENT_SPAN_ID, ThreadLocalConfig.DEFAULT_PARENT_SPAN_ID);
        }
    }
}
