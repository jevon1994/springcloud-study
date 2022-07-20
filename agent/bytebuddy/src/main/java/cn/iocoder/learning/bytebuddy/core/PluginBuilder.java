package cn.iocoder.learning.bytebuddy.core;

import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.Morph;
import net.bytebuddy.matcher.ElementMatchers;

import java.util.function.Supplier;

public class PluginBuilder {


    public <T> DynamicType.Builder<?> build(DynamicType.Builder<?> builder, String methodName, Supplier<T> supplier) {
        DynamicType.Builder.MethodDefinition.ReceiverTypeDefinition<?> intercept = builder
                .method(ElementMatchers.named(methodName))
                .intercept(MethodDelegation.withDefaultConfiguration()
                        .withBinders(Morph.Binder.install(OverrideCallable.class))
                        .to(supplier.get()));
        return intercept;
    }
}
