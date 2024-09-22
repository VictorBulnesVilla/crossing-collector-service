package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class CrossingControllerImpTest {
    private CrossingControllerImpl  crossingControllerImpl;
    private Crossing crossing;
    @Mock private CrossingPublisher crossingPublisher; 
    @BeforeEach public void SetUp(){
        crossingControllerImpl = new CrossingControllerImpl(crossingPublisher);
        crossing = new Crossing();
    }
    @Test
    public void addCrossing_returns202(){
        doNothing().when(crossingPublisher).publish(crossing);
        ResponseEntity<Void> responseEntity = crossingControllerImpl.addCrossing(crossing);
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
    }

    @Test
    public void addCrossing_publishingFails_returns500(){
        doThrow(new  RuntimeException()).when(crossingPublisher).publish(crossing);
        ResponseEntity<Void> responseEntity = crossingControllerImpl.addCrossing(crossing);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}
