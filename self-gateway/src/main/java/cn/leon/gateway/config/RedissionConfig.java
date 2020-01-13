//package cn.leon.gateway.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @ClassName RedissionConfig
// * @Description
// * @Author Jevon
// * @Date2020/1/13 11:11
// **/
//@Configuration
//public class RedissionConfig {
//
//    @Value("${spring.redis.host}")
//    private String host;
//
//    @Value("${spring.redis.port}")
//    private String port;
//
//    @Value("${spring.redis.password}")
//    private String password;
//
//    @Bean
//    public RedissonClient getRedisson(){
//        Config config = new Config();
//        config.useSingleServer().setAddress("redis://" + host + ":" + port);
//        //config.useSingleServer().setAddress("redis://" + host + ":" + port).setPassword(password);
//        //添加主从配置
//        //config.useMasterSlaveServers().setMasterAddress("").setPassword("").addSlaveAddress(new String[]{"", ""});
//        return Redisson.create(config);
//    }
//
//}
