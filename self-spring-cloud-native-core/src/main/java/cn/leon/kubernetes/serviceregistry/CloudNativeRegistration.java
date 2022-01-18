/*
 * Copyright 2013-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.leon.kubernetes.serviceregistry;

import org.springframework.cloud.client.serviceregistry.Registration;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class CloudNativeRegistration implements Registration, Closeable {


    private AtomicBoolean running = new AtomicBoolean(false);


    @Override
    public void close() throws IOException {
    }

    @Override
    public String getServiceId() {
        return "kubernetes";
    }

    @Override
    public String getHost() {
        return "localhost";
    }

    @Override
    public int getPort() {
        return 0;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public URI getUri() {
        return null;
    }

    @Override
    public Map<String, String> getMetadata() {
        return null;
    }

    @Override
    public String toString() {
        return "KubernetesRegistration{" + "client=" + ", properties="
                + ", running=" + this.running + '}';
    }

}
