package org.example;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ShutdownSignalException;

@Service
public class RabbitCrossingPublisher implements CrossingPublisher {
    private Connection conn;
    private String exchange;
    private String routingKey;
    public RabbitCrossingPublisher(ConnectionFactory factory, @Qualifier("exchangeName") String exchange, @Qualifier("routingKey") String routingKey){
        try {
            conn = factory.newConnection();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
            conn = null;
        } 
        this.exchange = exchange;
        this.routingKey = routingKey;
    }


    @Override
    public void publish(Crossing crossing) {  
        byte[] messageBodyBytes = crossing.toJson().getBytes(); 
        try(Channel channel = conn.createChannel()){
            channel.basicPublish(exchange, routingKey, null, messageBodyBytes);
        }catch(IOException | TimeoutException | ShutdownSignalException e){
            throw new IllegalStateException(e);
        }
    }
    
}

