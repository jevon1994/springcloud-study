/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package cn.iocoder.learning.bytebuddy.interceptor;

import cn.iocoder.learning.bytebuddy.core.OverrideCallable;
import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;

public interface InstanceMethodsAroundInterceptor {

    void beforeMethod(Object objInst, Method method, Object[] allArguments) throws Throwable;


    Object afterMethod(Object objInst, Method method, Object[] allArguments,
                       Object ret) throws Throwable;

    void handleMethodException(Object objInst, Method method, Object[] allArguments, Throwable t);

    @RuntimeType
    default Object intercept(@This Object obj,
                             @AllArguments Object[] allArguments,
                             @Origin Method method,
                             @Morph OverrideCallable callable
    ) throws Throwable {
        Object ret = null;
        try {
            beforeMethod(obj, method, allArguments);

            ret = callable.call(allArguments);

            return ret;
        } catch (Throwable t) {
            try {
                handleMethodException(obj, method, allArguments, t);
            } catch (Throwable t2) {
            }
            throw t;
        } finally {
            try {
                ret = afterMethod(obj, method, allArguments, ret);
            } catch (Throwable t) {

            }
        }
    }
}
