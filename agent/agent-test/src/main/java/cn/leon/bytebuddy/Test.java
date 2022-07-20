package cn.leon.bytebuddy;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static final Map<String, Collection<String>> map = new HashMap();
    public static void main(String[] args) {
        Map<String, Collection<String>> afterMap = Collections.unmodifiableMap(map);
        afterMap.put("1", Collections.singleton("1"));
    }
}
