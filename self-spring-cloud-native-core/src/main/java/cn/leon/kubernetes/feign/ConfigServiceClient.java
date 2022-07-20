package cn.leon.kubernetes.feign;

import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author mujian
 * @Classname ConfigServiceClient
 * @Description
 * @Date 2022/1/26
 */
@FeignClient(name = "config", url = "${wowjoy.cloud-native.config.url}")
public interface ConfigServiceClient {

    /**
     * 功能描述: 获取配置
     * @param: [name, profile]
     * @return: org.springframework.cloud.config.environment.Environment
     * @auther: mujian
     * @date: 2022/2/17 10:41
     */
    @GetMapping("{name}/{profile}")
    Environment getConfig(@PathVariable("name") String name,@PathVariable("profile") String profile);

    /**
     * 功能描述: 获取配置
     * @param: [name, profile, label]
     * @return: org.springframework.cloud.config.environment.Environment
     * @auther: mujian
     * @date: 2022/2/17 10:41
     */
    @GetMapping("{name}/{profile}/{label}")
    Environment getConfigByLabel(@PathVariable("name") String name, @PathVariable("profile") String profile,@PathVariable("label") String label);
}

