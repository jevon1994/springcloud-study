package cn.leon.kubernetes.enums;

/**
 * @author mujian
 * @Classname RegisterEnum
 * @Description
 * @Date 2022/1/26
 */
public enum RegisterEnum {
    /**
     同时注册 eureka, kubernetes
     */
    ALL,
    /**
     * 只注册 eureka
     */
    EUREKA,
    /**
     * 只注册 kubernetes
     */
    KUBERNETES;
}
