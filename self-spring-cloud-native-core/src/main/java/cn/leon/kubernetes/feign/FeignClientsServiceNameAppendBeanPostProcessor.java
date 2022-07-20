package cn.leon.kubernetes.feign;

import cn.leon.kubernetes.config.ConfigInitializable;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

/**
 * @author mujian
 * @Classname FeignClientsServiceNameAppendBeanPostProcessor
 * @Description
 * @Date 2022/3/4
 */
public class FeignClientsServiceNameAppendBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware, EnvironmentAware {

    public static final Logger log = LoggerFactory.getLogger(FeignClientsServiceNameAppendBeanPostProcessor.class);

    private ApplicationContext applicationContext;

    private Environment environment;

    private String beanNameOfFeignClientFactoryBean = "org.springframework.cloud.openfeign.FeignClientFactoryBean";

    @SneakyThrows
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (ConfigInitializable.feignProcessor.getAndIncrement() == 0) {
            Class beanNameClz;
            try {
                beanNameClz = Class.forName(beanNameOfFeignClientFactoryBean);
                Map<String, Object> beans = applicationContext.getBeansOfType(beanNameClz);
                if (beans != null) {
                    Class finalBeanNameClz = beanNameClz;
                    beans.forEach((feignBeanName, beanOfFeignClientFactoryBean) -> {
                        try {
                            changeUrl(finalBeanNameClz, beanOfFeignClientFactoryBean);
                        } catch (Exception e) {
                            log.error("feignClientsLocalInvokeBeanPostProcessor error", e);
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bean;
    }

    private void changeUrl(Class clazz, Object obj) throws Exception {
        Field nameField = ReflectionUtils.findField(clazz, "name");
        Field urlField = ReflectionUtils.findField(clazz, "url");
        if (Objects.nonNull(nameField)) {
            ReflectionUtils.makeAccessible(nameField);
            ReflectionUtils.makeAccessible(urlField);
            String name = (String) nameField.get(obj);
            String url = (String) urlField.get(obj);
            if ("cloud-gateway-admin".equals(name)) {
                if ("true".equals(environment.getProperty("cloud-native.debug"))) {
                    log.info("--------------Cloud Native name: {}, url: {}", name, url);
                }
            } else {
                String prefix = environment.getProperty("cloud-native.gateway.prefix");
                if (StringUtils.hasText(prefix) && !prefix.startsWith("http")) {
                    prefix = "http://" + prefix;
                }
                if (name.startsWith("http")) {
                    int idx = name.indexOf("//");
                    name = name.substring(idx + 1);
                }
                if (!name.startsWith("/")) {
                    name = "/" + name;
                }
                if (name.endsWith("/")) {
                    name = name.substring(0, name.length() - 1);
                }
                String newUrl = prefix.concat(name);
                ReflectionUtils.setField(urlField, obj, newUrl);
                if ("true".equals(environment.getProperty("cloud-native.debug"))) {
                    log.info("--------------Cloud Native name: {}, url[before: {}, after: {}]", nameField.get(obj), url, newUrl);
                }
            }
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
