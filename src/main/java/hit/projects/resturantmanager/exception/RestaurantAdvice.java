package hit.projects.resturantmanager.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class RestaurantAdvice {

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<Object> restaurantNotFoundHandler(RestaurantNotFoundException rnfe){
        Map<String, Object> body = new LinkedHashMap<>();

        body.put("time", LocalDateTime.now());
        body.put("message", rnfe.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RestaurantConflictException.class)
    public ResponseEntity<Object> restaurantConflictHandler(RestaurantConflictException rce){
        Map<String, Object> body = new LinkedHashMap<>();

        body.put("time", LocalDateTime.now());
        body.put("message", rce.getMessage());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }
}
