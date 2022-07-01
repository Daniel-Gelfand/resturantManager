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
    public ResponseEntity<Object> restaurantNotFoundHandler(RestaurantNotFoundException rnfe) {
        return new ResponseEntity<>(getStringMessage(rnfe.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RestaurantConflictException.class)
    public ResponseEntity<Object> restaurantConflictHandler(RestaurantConflictException rce) {
        return new ResponseEntity<>(getStringMessage(rce.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RestaurantGeneralException.class)
    public ResponseEntity<Object> restaurantGeneralExceptionHandler(RestaurantGeneralException rge) {
        return new ResponseEntity<>(getStringMessage(rge.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<String, Object> getStringMessage(String message) {
        Map<String, Object> body = new LinkedHashMap<>();

        body.put("time", LocalDateTime.now());
        body.put("message", message);
        return body;
    }
}
