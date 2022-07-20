package cn.leon.bytebuddy;

import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.util.concurrent.Callable;


public class SelfInterceptor {

    @RuntimeType
    public static Object intercept(@SuperCall Callable<?> zuper) throws Exception {
        System.out.println("intercept：拦截了");

//        return zuper.call();
        return 2;
    }
}
