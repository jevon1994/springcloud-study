package cn.iocoder.learning.bytebuddy.interceptor.tomcat;

import cn.iocoder.learning.bytebuddy.interceptor.InstanceMethodsAroundInterceptor;
import cn.iocoder.learning.bytebuddy.interceptor.feign.FeignInterceptor;
import cn.iocoder.learning.bytebuddy.trace.ThreadLocalConfig;
import cn.iocoder.learning.bytebuddy.trace.Trace;
import com.alibaba.ttl.TransmittableThreadLocal;
import org.apache.catalina.connector.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.UUID;

public class TomcatInvokeInterceptor implements InstanceMethodsAroundInterceptor {

    private static final Logger log = LogManager.getLogger(TomcatInvokeInterceptor.class);

    @Override
    public void beforeMethod(Object objInst, Method method, Object[] allArguments) throws Throwable {
        Request request = (Request) allArguments[0];

        String traceId = request.getHeader(ThreadLocalConfig.TRACE_ID);
        String lastSpanId = request.getHeader(ThreadLocalConfig.SPAN_ID);
        String lastParentSpanId = request.getHeader(ThreadLocalConfig.PARENT_SPAN_ID);

        Trace trace = StringUtils.isEmpty(traceId) ?
                Trace.builder()
                        .traceId(UUID.randomUUID().toString())
                        .parentSpanId(ThreadLocalConfig.DEFAULT_PARENT_SPAN_ID)
                        .spanId(ThreadLocalConfig.DEFAULT_SPAN_ID)
//                        .appName(appName)
                        .build() : Trace.builder()
                .traceId(traceId)
                .parentSpanId(lastParentSpanId)
                .spanId(lastSpanId)
//                .appName(appName)
                .build();

        TransmittableThreadLocal<Trace> transmittableThreadLocal = ThreadLocalConfig.getTransmittableThreadLocal();
        transmittableThreadLocal.set(trace);
    }

    @Override
    public Object afterMethod(Object objInst, Method method, Object[] allArguments, Object ret) throws Throwable {
        Request request = (Request) allArguments[0];
        HttpServletResponse response = (HttpServletResponse) allArguments[1];
        response.addHeader(ThreadLocalConfig.SERVER_RECEIVE_TIME, String.valueOf(System.currentTimeMillis()));
        Trace trace = ThreadLocalConfig.getTransmittableThreadLocal().get();
        log.info("traceId: {},parentspanid: {},spanid: {},srtime: {},sstime: {}", trace.getTraceId(), trace.getParentSpanId(), trace.getSpanId()
                , trace.getSrtime(), trace.getSstime());
        ThreadLocalConfig.getTransmittableThreadLocal().remove();
        return ret;
    }

    @Override
    public void handleMethodException(Object objInst, Method method, Object[] allArguments, Throwable t) {

    }
}
