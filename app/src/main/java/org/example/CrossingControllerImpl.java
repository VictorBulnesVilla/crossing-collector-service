package org.example;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CrossingControllerImpl implements CrossingController {
    private CrossingPublisher crossingPublisher;
    public CrossingControllerImpl(CrossingPublisher crossingPublisher){
        this.crossingPublisher = crossingPublisher;
    }

    @Override
    public ResponseEntity<Void> addCrossing(Crossing crossing) {
        try {
            crossingPublisher.publish(crossing);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }
    
}
