package cn.leon.trace.agent;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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
        addRequestHeader(request);
        ClientHttpResponse response = execution.execute(request, body);
        return response;
    }

    private void addRequestHeader(HttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        ThreadLocalConfig.TRACE trace = ThreadLocalConfig.getTransmittableThreadLocal().get();
        if (trace != null) {
            String traceId = trace.getTraceId();
            String spanId = trace.getSpanId();
            String parentSpanId = trace.getParentSpanId();

            headers.add(ThreadLocalConfig.TRACE_ID, traceId);
            long i = Long.valueOf(spanId) + 1;
            headers.add(ThreadLocalConfig.SPAN_ID, String.valueOf(i).concat(".").concat(String.valueOf(Thread.currentThread().getId())));
            headers.add(ThreadLocalConfig.PARENT_SPAN_ID, parentSpanId);
            headers.add(ThreadLocalConfig.APP_NAME, trace.getAppName());

        } else {
            headers.add(ThreadLocalConfig.TRACE_ID, UUID.randomUUID().toString());
            headers.add(ThreadLocalConfig.APP_NAME, appName);
            headers.add(ThreadLocalConfig.SPAN_ID, ThreadLocalConfig.DEFAULT_SPAN_ID);
            headers.add(ThreadLocalConfig.PARENT_SPAN_ID, ThreadLocalConfig.DEFAULT_PARENT_SPAN_ID);
        }
        log.info("appname: {},headers: {}",appName,headers);
    }
}
