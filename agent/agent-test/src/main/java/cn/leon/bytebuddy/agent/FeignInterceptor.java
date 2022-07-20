package cn.leon.bytebuddy.agent;

import net.bytebuddy.implementation.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class FeignInterceptor {

    private static final Logger logger =  LogManager.getLogger(FeignInterceptor.class);

    @RuntimeType
    public Object intercept(@This Object obj, @AllArguments Object[] allArguments, @Origin Method method,
                            @SuperCall Callable<?> zuper) throws Throwable {
        logger.info("{},{},{},{}",zuper , obj , allArguments , method);
        return obj;
    }

    @RuntimeType
    public static Object intercept(@SuperCall Callable<?> zuper) throws Exception {
        return zuper;
    }
}
