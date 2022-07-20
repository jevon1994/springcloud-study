package cn.leon.kubernetes.autoconfig;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.kubernetes.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mujian
 * @Classname KubernetesConfiguration
 * @Description Info endpoint
 * @Date 2022/2/15
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "cloud-native.discovery", name = "type", havingValue = "kubernetes")
public class KubernetesConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public KubernetesClient kubernetesClient(Config config) {
        return new DefaultKubernetesClient(config);
    }

    @Bean
    @ConditionalOnMissingBean
    public StandardPodUtils kubernetesPodUtils(KubernetesClient client) {
        return new StandardPodUtils(client);
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(HealthIndicator.class)
    protected static class KubernetesActuatorConfiguration {

        @Bean
        @ConditionalOnEnabledHealthIndicator("kubernetes")
        public KubernetesHealthIndicator kubernetesHealthIndicator(PodUtils podUtils) {
            return new KubernetesHealthIndicator(podUtils);
        }

        @Bean
        public KubernetesInfoContributor kubernetesInfoContributor(PodUtils podUtils) {
            return new KubernetesInfoContributor(podUtils);
        }
    }

    @Bean
    @ConditionalOnMissingBean(Config.class)
    public Config kubernetesClientConfig(
            KubernetesClientProperties kubernetesClientProperties) {
        Config base = Config.autoConfigure(null);
        Config properties = new ConfigBuilder(base)
                // Only set values that have been explicitly specified
                // .withMasterUrl(or(kubernetesClientProperties.getMasterUrl(),
                //         base.getMasterUrl()))
                // .withApiVersion(or(kubernetesClientProperties.getApiVersion(),
                //         base.getApiVersion()))
                // .withNamespace(or(kubernetesClientProperties.getNamespace(),
                //         base.getNamespace()))
                // .withUsername(
                //         or(kubernetesClientProperties.getUsername(), base.getUsername()))
                // .withPassword(
                //         or(kubernetesClientProperties.getPassword(), base.getPassword()))
                //
                // .withCaCertFile(or(kubernetesClientProperties.getCaCertFile(),
                //         base.getCaCertFile()))
                // .withCaCertData(or(kubernetesClientProperties.getCaCertData(),
                //         base.getCaCertData()))
                //
                // .withClientKeyFile(or(kubernetesClientProperties.getClientKeyFile(),
                //         base.getClientKeyFile()))
                // .withClientKeyData(or(kubernetesClientProperties.getClientKeyData(),
                //         base.getClientKeyData()))
                //
                // .withClientCertFile(or(kubernetesClientProperties.getClientCertFile(),
                //         base.getClientCertFile()))
                // .withClientCertData(or(kubernetesClientProperties.getClientCertData(),
                //         base.getClientCertData()))
                //
                // // No magic is done for the properties below so we leave them as is.
                // .withClientKeyAlgo(or(kubernetesClientProperties.getClientKeyAlgo(),
                //         base.getClientKeyAlgo()))
                // .withClientKeyPassphrase(
                //         or(kubernetesClientProperties.getClientKeyPassphrase(),
                //                 base.getClientKeyPassphrase()))
                // .withConnectionTimeout(
                //         orDurationInt(kubernetesClientProperties.getConnectionTimeout(),
                //                 base.getConnectionTimeout()))
                // .withRequestTimeout(
                //         orDurationInt(kubernetesClientProperties.getRequestTimeout(),
                //                 base.getRequestTimeout()))
                // .withRollingTimeout(
                //         orDurationLong(kubernetesClientProperties.getRollingTimeout(),
                //                 base.getRollingTimeout()))
                // .withTrustCerts(or(kubernetesClientProperties.isTrustCerts(),
                //         base.isTrustCerts()))
                // .withHttpProxy(or(kubernetesClientProperties.getHttpProxy(),
                //         base.getHttpProxy()))
                // .withHttpsProxy(or(kubernetesClientProperties.getHttpsProxy(),
                //         base.getHttpsProxy()))
                // .withProxyUsername(or(kubernetesClientProperties.getProxyUsername(),
                //         base.getProxyUsername()))
                // .withPassword(or(kubernetesClientProperties.getProxyPassword(),
                //         base.getProxyPassword()))
                // .withNoProxy(
                //         or(kubernetesClientProperties.getNoProxy(), base.getNoProxy()))
                .build();

        // if (properties.getNamespace() == null || properties.getNamespace().isEmpty()) {
        //     LOG.warn("No namespace has been detected. Please specify "
        //             + "KUBERNETES_NAMESPACE env var, or use a later kubernetes version (1.3 or later)");
        // }
        return properties;
    }

}