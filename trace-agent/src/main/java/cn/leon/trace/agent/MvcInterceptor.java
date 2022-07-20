package cn.leon.trace.agent;

import cn.leon.trace.agent.config.ThreadLocalConfig;
import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
public class MvcInterceptor extends HandlerInterceptorAdapter {

    private String appName;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String traceId = request.getHeader(ThreadLocalConfig.TRACE_ID);
        String lastSpanId = request.getHeader(ThreadLocalConfig.SPAN_ID);
        String lastParentSpanId = request.getHeader(ThreadLocalConfig.PARENT_SPAN_ID);

        Trace trace = StringUtils.isEmpty(traceId) ?
                Trace.builder()
                        .traceId(UUID.randomUUID().toString())
                        .parentSpanId(ThreadLocalConfig.DEFAULT_PARENT_SPAN_ID)
                        .spanId(ThreadLocalConfig.DEFAULT_SPAN_ID)
                        .appName(appName)
                        .build() : Trace.builder()
                .traceId(traceId)
                .parentSpanId(lastParentSpanId)
                .spanId(lastSpanId)
                .appName(appName)
                .build();

        TransmittableThreadLocal<Trace> transmittableThreadLocal = ThreadLocalConfig.getTransmittableThreadLocal();
        transmittableThreadLocal.set(trace);

        response.addHeader(ThreadLocalConfig.SERVER_RECEIVE_TIME, String.valueOf(System.currentTimeMillis()));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        Trace trace = ThreadLocalConfig.getTransmittableThreadLocal().get();
        log.info("appname: {},traceId: {},parentspanid: {},spanid: {},srtime: {},sstime: {}", appName, trace.getTraceId(), trace.getParentSpanId(), trace.getSpanId()
                , trace.getSrtime(), trace.getSstime());
        ThreadLocalConfig.getTransmittableThreadLocal().remove();
    }
}