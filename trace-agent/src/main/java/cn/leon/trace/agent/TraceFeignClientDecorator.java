package cn.leon.trace.agent;

import cn.leon.trace.agent.config.ThreadLocalConfig;
import feign.Client;
import feign.Request;
import feign.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class TraceFeignClientDecorator implements Client {

    private final Client delegate;

    @Override
    public Response execute(Request request, Request.Options options) throws IOException {
        Trace trace = ThreadLocalConfig.getTransmittableThreadLocal().get();
        trace.setCstime(String.valueOf(System.currentTimeMillis()));
        Response response = delegate.execute(request, options);
        trace.setCrtime(String.valueOf(System.currentTimeMillis()));
        log.info("traceId: {},parentspanId: {},spanId: {},cstime: {},crtime: {}", trace.getTraceId(), trace.getParentSpanId(), trace.getSpanId(), trace.getCstime(), trace.getCrtime());
        return response;
    }
}
