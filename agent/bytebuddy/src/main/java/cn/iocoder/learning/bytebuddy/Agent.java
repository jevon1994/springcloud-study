package cn.iocoder.learning.bytebuddy;

import cn.iocoder.learning.bytebuddy.core.AbstractJunction;
import cn.iocoder.learning.bytebuddy.core.PluginBuilder;
import cn.iocoder.learning.bytebuddy.interceptor.feign.FeignInterceptor;
import cn.iocoder.learning.bytebuddy.interceptor.resttemplate.RestemplateRequestInterceptor;
import cn.iocoder.learning.bytebuddy.interceptor.tomcat.TomcatInvokeInterceptor;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.NamedElement;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;
import java.util.HashMap;
import java.util.Map;

import static net.bytebuddy.matcher.ElementMatchers.isInterface;
import static net.bytebuddy.matcher.ElementMatchers.not;

public class Agent {

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        Map<String,String> nameMatchMap = new HashMap() {{
            put("feign.Client$Default", "execute" );
            put("org.springframework.web.client.RestTemplate", "createRequest");
            put("org.apache.catalina.core.StandardHostValve","invoke");
        }};
        ElementMatcher.Junction judge = new AbstractJunction<NamedElement>() {
            @Override
            public boolean matches(NamedElement target) {
                return nameMatchMap.containsKey(target.getActualName());
            }
        };
        judge = judge.and(not(isInterface()));
        final PluginBuilder pluginBuilder = new PluginBuilder();
        new AgentBuilder.Default()
                .type(judge)
                .transform(new AgentBuilder.Transformer() {
                    @Override
                    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule) {
                        if("feign.Client$Default".equals(typeDescription.getName())){
                            builder = pluginBuilder.build(builder, "execute", () -> new FeignInterceptor());
                        }
                        if("org.springframework.web.client.RestTemplate".equals(typeDescription.getName())){
                            builder = pluginBuilder.build(builder, "createRequest", () -> new RestemplateRequestInterceptor());
                        }
                        if("org.apache.catalina.core.StandardHostValve".equals(typeDescription.getName())){
                            builder = pluginBuilder.build(builder, "invoke", () -> new TomcatInvokeInterceptor());
                        }
                        return builder;
                    }
                })
                .installOn(instrumentation);
    }
}
