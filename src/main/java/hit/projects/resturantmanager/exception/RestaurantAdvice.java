package hit.projects.resturantmanager.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

//TODO: בתאכלס אין סיבה לעשות לכל סרביס קונטרול אדוויס משלו, אפשר לבנות 1 לכולם ולהוסיף בו כמה סוגי החזרות שונים כמו ה-404 שהחזרנו
@ControllerAdvice

public class RestaurantAdvice {

    @ExceptionHandler(MenuItemException.class)
    public ResponseEntity<Object> menuItemNotFoundHandler(MenuItemException mie){
        Map<String, Object> body = new LinkedHashMap<>();

        body.put("time", LocalDateTime.now());
        body.put("message", mie.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TableException.class)
    public ResponseEntity<Object> tableNotFound(TableException tableException) {
        Map<String, Object> body = new LinkedHashMap<>();

        body.put("time", LocalDateTime.now());
        body.put("message", tableException.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }





    String WaiterOnDutyNotFoundHandler(WaiterException waiterException){
        return waiterException.getMessage();
    }

    String menuItemPriceNotFoundHandler(MenuItemException mie) {
        return mie.getMessage();
    }
}
