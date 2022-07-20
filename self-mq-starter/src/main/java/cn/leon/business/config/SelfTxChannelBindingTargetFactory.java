package cn.leon.business.config;

import cn.leon.business.service.ITransactionMessageManagementService;
import org.springframework.cloud.stream.binder.rabbit.properties.RabbitExtendedBindingProperties;
import org.springframework.cloud.stream.binding.AbstractBindingTargetFactory;
import org.springframework.cloud.stream.binding.MessageChannelConfigurer;
import org.springframework.cloud.stream.config.BindingServiceProperties;
import org.springframework.cloud.stream.messaging.Source;

public class SelfTxChannelBindingTargetFactory extends AbstractBindingTargetFactory<SelfTxChannel> {

    private final MessageChannelConfigurer messageChannelConfigurer;

    private final ITransactionMessageManagementService managementService;

    private BindingServiceProperties bindingServiceProperties;

    private RabbitExtendedBindingProperties rabbitExtendedBindingProperties;


    public SelfTxChannelBindingTargetFactory(MessageChannelConfigurer messageChannelConfigurer,
                                             ITransactionMessageManagementService managementService,
                                             BindingServiceProperties bindingServiceProperties,
                                             RabbitExtendedBindingProperties rabbitExtendedBindingProperties) {
        super(SelfTxChannel.class);
        this.messageChannelConfigurer = messageChannelConfigurer;
        this.managementService = managementService;
        this.bindingServiceProperties = bindingServiceProperties;
        this.rabbitExtendedBindingProperties = rabbitExtendedBindingProperties;

    }

    @Override
    public SelfTxChannel createInput(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SelfTxChannel createOutput(String name) {
        SelfTxChannelImpl selfTxChannel = new SelfTxChannelImpl(managementService,bindingServiceProperties,rabbitExtendedBindingProperties);
        selfTxChannel.setAttribute("type", Source.OUTPUT);
        this.messageChannelConfigurer.configureOutputChannel(selfTxChannel, name);
        return selfTxChannel;
    }
}
