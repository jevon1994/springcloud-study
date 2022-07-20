package cn.iocoder.learning.bytebuddy.interceptor.feign;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HeaderMap {

    private Map<String, Collection<String>> headerMap;

    public HeaderMap() {
        headerMap = new HashMap<>();
    }

    public void put(String key, String val) {
        headerMap.put(key, Collections.singletonList(val));
    }


    public void putAll(Map<String, Collection<String>> map) {
        headerMap.putAll(map);
    }


    public Map<String, Collection<String>> getHeaderMap() {
        return headerMap;
    }


}
