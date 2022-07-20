package cn.leon.bytebuddy.agent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

public class Agent {

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        new AgentBuilder.Default()
                .type(ElementMatchers.any())
                .transform(new AgentBuilder.Transformer() {
                    @Override
                    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule) {
                        DynamicType.Builder.MethodDefinition.ReceiverTypeDefinition<?> intercept = builder
                                .method(ElementMatchers.named("execute"))
                                .intercept(MethodDelegation.to(FeignInterceptor.class));
                        return intercept;
                    }
                })
                .installOn(instrumentation);
    }
}
