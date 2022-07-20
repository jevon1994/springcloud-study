//package cn.leon.bytebuddy;
//
//import net.bytebuddy.agent.builder.AgentBuilder;
//import net.bytebuddy.description.type.TypeDescription;
//import net.bytebuddy.dynamic.DynamicType;
//import net.bytebuddy.implementation.MethodDelegation;
//import net.bytebuddy.matcher.ElementMatchers;
//import net.bytebuddy.utility.JavaModule;
//
//import java.lang.instrument.Instrumentation;
//
//public class Agent {
//    public static void premain(String agentArgs, Instrumentation instrumentation) {
//
//        // proxy create
//        new AgentBuilder.Default()
//                .type(ElementMatchers.any())
//                .transform(new AgentBuilder.Transformer() {
//
//                    @Override
//                    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule) {
//                        return builder
//                                .method(ElementMatchers.named("hello"))
//                                .intercept(MethodDelegation.to(SelfInterceptor.class))
//                                ;
//                    }
//                })
//                .with(new AgentBuilder.Listener() {
//
//                    @Override
//                    public void onDiscovery(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
//
//                    }
//
//                    @Override
//                    public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded, DynamicType dynamicType) {
//                        System.out.println("onTransformation ：" + typeDescription);
//                        System.out.println("onTransformation ：" + dynamicType);
//                    }
//
//                    @Override
//                    public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded) {
//
//                    }
//
//                    @Override
//                    public void onError(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded, Throwable throwable) {
//
//                    }
//
//                    @Override
//                    public void onComplete(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
//
//                    }
//                })
//                .installOn(instrumentation);
//    }
//
//}
