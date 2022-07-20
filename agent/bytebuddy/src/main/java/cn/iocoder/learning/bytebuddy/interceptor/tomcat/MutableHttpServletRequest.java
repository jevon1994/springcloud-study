package cn.iocoder.learning.bytebuddy.interceptor.tomcat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;

public class MutableHttpServletRequest extends HttpServletRequestWrapper {
    private final Map<String, String> customHeaders;

    public MutableHttpServletRequest(HttpServletRequest request) {
        super(request);
        this.customHeaders = new HashMap<String, String>();
    }

    public void putHeader(String name, String value) {
        this.customHeaders.put(name, value);
    }

    public Map<String, String> getCustomHeaders() {
        return customHeaders;
    }
}
