package org.example;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.client.ConnectionFactory;

@Configuration
public class AppConfiguration {
    @Bean
    public ConnectionFactory getConnectionFactory(){
        String rabbitUri = System.getenv("RABBIT_URI");
        ConnectionFactory factory = new ConnectionFactory();
        try {
            factory.setUri(rabbitUri);
        } catch (KeyManagementException | NoSuchAlgorithmException | URISyntaxException e) {
            throw new IllegalStateException(e);
        }

        return factory;
         
    }

    @Bean("exchangeName")
    public String getExchangeName(){
        return System.getenv("RABBIT_EXCHANGE");      
    }

    @Bean("routingKey")
    public String getRoutingKey(){
        return System.getenv("RABBIT_ROUTING_KEY");       
    }
}
