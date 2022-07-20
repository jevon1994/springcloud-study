//package cn.leon.order.config;
//
//import com.alibaba.ttl.TransmittableThreadLocal;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.util.StringUtils;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Slf4j
//@AllArgsConstructor
//public class MvcInterceptor extends HandlerInterceptorAdapter {
//
//    private String appName;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//
//        String traceId = request.getHeader(ThreadLocalConfig.TRACE_ID);
//        String currspanId = request.getHeader(ThreadLocalConfig.SPAN_ID);
//        String currparentSpanId = request.getHeader(ThreadLocalConfig.PARENT_SPAN_ID);
//        String srtime = request.getHeader(ThreadLocalConfig.SERVER_RECEIVE_TIME);
//        String sstime = request.getHeader(ThreadLocalConfig.SERVER_SEND_TIME);
//        String crtime = request.getHeader(ThreadLocalConfig.CLIENT_RECEIVE_TIME);
//        String cstime = request.getHeader(ThreadLocalConfig.CLIENT_SEND_TIME);
//
//        Trace trace = StringUtils.isEmpty(traceId) ? ThreadLocalConfig.getDefaultTrace(appName)
//                : ThreadLocalConfig.getCurrentTrace(appName,traceId,currparentSpanId,currspanId);
//
//        TransmittableThreadLocal<Trace> threadLocal = ThreadLocalConfig.getTransmittableThreadLocal();
//        threadLocal.set(trace);
//        log.info("service send,appname: {},traceId: {},parentspanid: {},spanid: {}",appName,trace.getTraceId(),trace.getParentSpanId(),trace.getSpanId());
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//        // 请求结束删除信息
//        ThreadLocalConfig.getTransmittableThreadLocal().remove();
//    }
//}