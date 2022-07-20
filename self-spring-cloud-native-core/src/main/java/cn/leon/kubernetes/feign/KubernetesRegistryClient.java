
package cn.leon.kubernetes.feign;

import cn.leon.kubernetes.decoder.CloudNativeRegisterConfiguration;
import cn.leon.kubernetes.model.RegisterRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author mujian
 * @Classname KubernetesRegistryClient
 * @Description
 * @Date 2021/12/27
 */
@FeignClient(name = "cloud-gateway-admin", url = "${wowjoy.cloud-native.discovery.url}"
        , configuration = CloudNativeRegisterConfiguration.class)
public interface KubernetesRegistryClient {

    /**
     * 功能描述: 注册 svc
     * @param: [name, registerRequest]
     * @return: java.lang.String
     * @auther: mujian
     * @date: 2022/2/17 10:41
     */
    @PostMapping(value = "/app/v1/services/{name}",consumes = MediaType.APPLICATION_JSON_VALUE)
    String register(@PathVariable("name") String name, @RequestBody RegisterRequest registerRequest);
}
