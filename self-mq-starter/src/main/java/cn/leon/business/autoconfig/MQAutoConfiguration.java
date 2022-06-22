package cn.leon.mq.autoconfig;

import cn.leon.mq.config.SelfTxChannelBindingTargetFactory;
import cn.leon.mq.dao.TransactionalMessageContentDao;
import cn.leon.mq.dao.TransactionalMessageDao;
import cn.leon.mq.dao.impl.TransactionalMessageContentDaoImpl;
import cn.leon.mq.dao.impl.TransactionalMessageDaoImpl;
import cn.leon.mq.service.ITransactionMessageManagementService;
import cn.leon.mq.service.ITransactionMessageService;
import cn.leon.mq.service.impl.TransactionMessageManagementServiceImpl;
import cn.leon.mq.service.impl.TransactionMessageServiceImpl;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cloud.stream.binder.rabbit.properties.RabbitExtendedBindingProperties;
import org.springframework.cloud.stream.binding.CompositeMessageChannelConfigurer;
import org.springframework.cloud.stream.config.BindingServiceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class MQAutoConfiguration {

    @Bean
    @Primary
    public SelfTxChannelBindingTargetFactory selfTxFactory(
            CompositeMessageChannelConfigurer compositeMessageChannelConfigurer,
            ITransactionMessageManagementService managementService,
            BindingServiceProperties bindingServiceProperties,
            RabbitExtendedBindingProperties rabbitExtendedBindingProperties) {
        return new SelfTxChannelBindingTargetFactory(
                compositeMessageChannelConfigurer,
                managementService,
                bindingServiceProperties,
                rabbitExtendedBindingProperties);
    }

    @Bean
    public TransactionalMessageDao transactionalMessageDao(JdbcTemplate jdbcTemplate){
        return new TransactionalMessageDaoImpl(jdbcTemplate);
    }

    @Bean
    public TransactionalMessageContentDao transactionalMessageContentDao(JdbcTemplate jdbcTemplate){
        return new TransactionalMessageContentDaoImpl(jdbcTemplate);
    }

    @Bean
    public ITransactionMessageManagementService managementService(TransactionalMessageDao messageDao, TransactionalMessageContentDao contentDao, RabbitTemplate rabbitTemplate){
        return new TransactionMessageManagementServiceImpl(messageDao,contentDao,rabbitTemplate);
    }
    @Bean
    public ITransactionMessageService transactionMessageService(AmqpAdmin amqpAdmin,ITransactionMessageManagementService managementService){
        return new TransactionMessageServiceImpl(amqpAdmin,managementService);
    }
}
