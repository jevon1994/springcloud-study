package cn.leon.kubernetes.config;

import cn.leon.kubernetes.feign.ConfigServiceClient;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.stereotype.Service;

@Service
public class ConfingInitializeImpl implements ConfingInitialize {

    private ConfigServiceClient configServiceClient;

    public ConfingInitializeImpl(ConfigServiceClient configServiceClient) {
        this.configServiceClient = configServiceClient;
    }


    @Override
    public Environment getConfig() {
        return configServiceClient.getConfig();
    }
}
