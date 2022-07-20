package cn.iocoder.learning.bytebuddy.interceptor.feign;

import cn.iocoder.learning.bytebuddy.interceptor.InstanceMethodsAroundInterceptor;
import cn.iocoder.learning.bytebuddy.trace.ThreadLocalConfig;
import cn.iocoder.learning.bytebuddy.trace.Trace;
import feign.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.UUID;

public class FeignInterceptor implements InstanceMethodsAroundInterceptor {

    private static final Logger log = LogManager.getLogger(FeignInterceptor.class);

    @Override
    public void beforeMethod(Object objInst, Method method, Object[] allArguments) throws Throwable {
        Request request = (Request) allArguments[0];


        HeaderMap headerMap = new HeaderMap();
        headerMap.putAll(request.headers());

        final Trace trace = ThreadLocalConfig.getTransmittableThreadLocal().get();
        if (Objects.nonNull(trace)) {
            String traceId = trace.getTraceId();
            String spanId = trace.getSpanId();

            headerMap.put(ThreadLocalConfig.TRACE_ID, traceId);
            headerMap.put(ThreadLocalConfig.SPAN_ID, trace.nextSpanId(trace.getParentSpanId(), spanId));
            headerMap.put(ThreadLocalConfig.PARENT_SPAN_ID, spanId);
            headerMap.put(ThreadLocalConfig.APP_NAME, trace.getAppName());
        } else {
            headerMap.put(ThreadLocalConfig.TRACE_ID, UUID.randomUUID().toString());
            headerMap.put(ThreadLocalConfig.SPAN_ID, ThreadLocalConfig.DEFAULT_SPAN_ID);
            headerMap.put(ThreadLocalConfig.PARENT_SPAN_ID, ThreadLocalConfig.DEFAULT_PARENT_SPAN_ID);
        }


        allArguments[0] = Request.create(request.method(), request.url(), headerMap.getHeaderMap(), request.body(),
                request.charset());
    }

    @Override
    public Object afterMethod(Object objInst, Method method, Object[] allArguments, Object ret) throws Throwable {
        return ret;
    }

    @Override
    public void handleMethodException(Object objInst, Method method, Object[] allArguments, Throwable t) {

    }
}
