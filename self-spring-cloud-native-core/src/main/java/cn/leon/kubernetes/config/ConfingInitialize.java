package cn.leon.kubernetes.config;


import org.springframework.cloud.config.environment.Environment;

// step1 获取 配置
public interface ConfingInitialize {

    Environment getConfig();
}
