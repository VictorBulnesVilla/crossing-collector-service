package org.example;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

import java.time.Instant;

public class RabbitCrossingPublisherTest {
    private Connection conn;
    private String queueName = "test3";
    private String exchangeName = "testExchange4";
    private String routingKey = "testkey2";
    private RabbitCrossingPublisher rabbitCrossingPublisher;

    @BeforeEach
    void setUp() throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        // "guest"/"guest" by default, limited to localhost connections
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        factory.setHost("localhost");
        factory.setPort(5672);
        conn = factory.newConnection();
        try(Channel channel = conn.createChannel()){      
            channel.exchangeDeclare(exchangeName, "direct", true);
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchangeName, routingKey);
        }
        
        rabbitCrossingPublisher = new RabbitCrossingPublisher(factory,exchangeName,routingKey);
    }


    @Test
    void publish_publishMessageToRabbit() throws Exception {
        Crossing crossing = new Crossing("bus",Instant.now(),1);
        String responseString;
        rabbitCrossingPublisher.publish(crossing);
        try(Channel channel = conn.createChannel()){
            GetResponse response = channel.basicGet(queueName, true);
            byte[] body = response.getBody();
            responseString = new String(body);  
        }
        assertEquals(crossing.toJson(), responseString);
    }

    @Test
    void publish_exchangeDoesNotExist_throwsIllegalStateException() throws Exception {
        Crossing crossing = new Crossing("bus",Instant.now(),1);
        try(Channel channel = conn.createChannel()){
            channel.queueDelete(queueName);
            channel.exchangeDelete(exchangeName);
        }
        assertThrows(IllegalStateException.class, () ->  rabbitCrossingPublisher.publish(crossing));
    }

    @AfterEach
    void cleanUp() throws Exception{
        try(Channel channel = conn.createChannel()){      
            channel.queueDelete(queueName);
        }
        conn.close();
    }
}
