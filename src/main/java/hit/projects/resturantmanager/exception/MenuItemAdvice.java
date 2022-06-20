package hit.projects.resturantmanager.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class MenuItemAdvice {

    @ExceptionHandler(MenuItemException.class)
    public ResponseEntity<Object> menuItemNotFoundHandler(MenuItemException mie){
        Map<String, Object> body = new LinkedHashMap<>();

        body.put("time", LocalDateTime.now());
        body.put("message", mie.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }


    String menuItemPriceNotFoundHandler(MenuItemException mie) {
        return mie.getMessage();
    }
}
