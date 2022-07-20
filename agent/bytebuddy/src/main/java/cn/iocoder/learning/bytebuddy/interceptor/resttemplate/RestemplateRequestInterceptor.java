package cn.iocoder.learning.bytebuddy.interceptor.resttemplate;

import cn.iocoder.learning.bytebuddy.interceptor.InstanceMethodsAroundInterceptor;
import cn.iocoder.learning.bytebuddy.trace.ThreadLocalConfig;
import cn.iocoder.learning.bytebuddy.trace.Trace;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.AbstractClientHttpRequest;
import org.springframework.http.client.ClientHttpRequest;

import java.lang.reflect.Method;
import java.util.UUID;

public class RestemplateRequestInterceptor implements InstanceMethodsAroundInterceptor {

    public static final Logger log = LogManager.getLogger(RestemplateRequestInterceptor.class);

    @Override
    public void beforeMethod(Object objInst, Method method, Object[] allArguments) throws Throwable {

    }

    @Override
    public Object afterMethod(Object objInst, Method method, Object[] allArguments, Object ret) throws Throwable {
        ClientHttpRequest clientHttpRequest = (ClientHttpRequest) ret;
        if (clientHttpRequest instanceof AbstractClientHttpRequest) {
            AbstractClientHttpRequest httpRequest = (AbstractClientHttpRequest) clientHttpRequest;
            Trace trace = ThreadLocalConfig.getTransmittableThreadLocal().get();
            addRequestHeader(httpRequest, trace);
            trace.setCrtime(ThreadLocalConfig.now());
            log.info("traceId: {},parentspanId: {},spanId: {},cstime: {},crtime: {}", trace.getTraceId(), trace.getParentSpanId(), trace.getSpanId(), trace.getCstime(), trace.getCrtime());
            return httpRequest;
        }
        return ret;
    }

    private void addRequestHeader(HttpRequest request, Trace trace) {
        HttpHeaders headers = request.getHeaders();

        if (trace != null) {
            String traceId = trace.getTraceId();
            String spanId = trace.getSpanId();

            headers.add(ThreadLocalConfig.TRACE_ID, traceId);
            headers.add(ThreadLocalConfig.SPAN_ID, trace.nextSpanId(trace.getParentSpanId(), spanId));
            headers.add(ThreadLocalConfig.PARENT_SPAN_ID, spanId);
            headers.add(ThreadLocalConfig.APP_NAME, trace.getAppName());
        } else {
            headers.add(ThreadLocalConfig.TRACE_ID, UUID.randomUUID().toString());
//            headers.add(ThreadLocalConfig.APP_NAME, appName);
            headers.add(ThreadLocalConfig.SPAN_ID, ThreadLocalConfig.DEFAULT_SPAN_ID);
            headers.add(ThreadLocalConfig.PARENT_SPAN_ID, ThreadLocalConfig.DEFAULT_PARENT_SPAN_ID);
        }
    }

    @Override
    public void handleMethodException(Object objInst, Method method, Object[] allArguments, Throwable t) {

    }
}
