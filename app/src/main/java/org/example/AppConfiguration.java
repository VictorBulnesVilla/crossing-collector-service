package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.client.ConnectionFactory;

@Configuration
public class AppConfiguration {
    @Bean
    public ConnectionFactory getConnectionFactory(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        factory.setHost("rabbitmq");
        factory.setPort(5672);
        
        return factory;
         
    }

    @Bean("exchangeName")
    public String getExchangeName(){
        return "exchangeRun";       
    }

    @Bean("routingKey")
    public String getRoutingKey(){
        return "testkey2";        
    }
}
