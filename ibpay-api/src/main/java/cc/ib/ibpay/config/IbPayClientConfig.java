package cc.ib.ibpay.config;

import cc.ib.ibpay.http.client.IbPayClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IbPayClientConfig {

    @Bean
    @ConfigurationProperties(prefix = "ibpay")
    public IbPayClient setIbPayClient(){
        IbPayClient client = new IbPayClient();
        return client;
    }
}
