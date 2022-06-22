package cn.leon.trace.agent;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@AllArgsConstructor
public class MvcInterceptor extends HandlerInterceptorAdapter {

    private String appName;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String traceId = request.getHeader(ThreadLocalConfig.TRACE_ID);
        String spanId = request.getHeader(ThreadLocalConfig.SPAN_ID);
        String parentSpanId = request.getHeader(ThreadLocalConfig.PARENT_SPAN_ID);
        String srtime = request.getHeader(ThreadLocalConfig.SERVER_RECEIVE_TIME);
        String sstime = request.getHeader(ThreadLocalConfig.SERVER_SEND_TIME);
        String crtime = request.getHeader(ThreadLocalConfig.CLIENT_RECEIVE_TIME);
        String cstime = request.getHeader(ThreadLocalConfig.CLIENT_SEND_TIME);

        ThreadLocalConfig.TRACE trace = null;
        if (StringUtils.isEmpty(traceId)) {
            traceId = UUID.randomUUID().toString();
            trace = new ThreadLocalConfig.TRACE(traceId, ThreadLocalConfig.DEFAULT_SPAN_ID, ThreadLocalConfig.DEFAULT_PARENT_SPAN_ID, null, null, null, null, appName);
        } else {
            trace = new ThreadLocalConfig.TRACE(traceId, String.valueOf(Long.valueOf(parentSpanId) + 1), spanId, null, null, null, null, appName);
        }
        TransmittableThreadLocal<ThreadLocalConfig.TRACE> threadLocal = ThreadLocalConfig.getTransmittableThreadLocal();
        threadLocal.set(trace);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        // 请求结束删除信息
        ThreadLocalConfig.getTransmittableThreadLocal().remove();
    }
}