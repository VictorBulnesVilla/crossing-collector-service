package org.example;

import org.springframework.http.ResponseEntity;

public interface CrossingController {
    public ResponseEntity<Void> addCrossing(Crossing crossing);
}
