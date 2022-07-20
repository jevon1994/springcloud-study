package cn.leon.order.sink;

import cn.leon.business.config.SelfTxChannel;
import org.springframework.cloud.stream.annotation.Output;

public interface AccountSink {
    String ACCOUNT_OUT_0 = "account-out-0";

    @Output(ACCOUNT_OUT_0)
    SelfTxChannel output();

}
