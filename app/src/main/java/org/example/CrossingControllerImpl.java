package org.example;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrossingControllerImpl implements CrossingController {
    private CrossingPublisher crossingPublisher;
    public CrossingControllerImpl(CrossingPublisher crossingPublisher){
        this.crossingPublisher = crossingPublisher;
    }

    @Override
    @PostMapping("/crossings")
    public ResponseEntity<Void> addCrossing(@RequestBody Crossing crossing) {
        try {
            crossingPublisher.publish(crossing);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }

    @RequestMapping("/test")
    public String getGreeting() {
        return "Hello Test!";
    }
    
}
