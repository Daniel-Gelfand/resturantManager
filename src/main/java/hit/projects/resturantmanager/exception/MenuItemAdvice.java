package hit.projects.resturantmanager.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MenuItemAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MenuItemException.class)
    String menuItemNotFoundHandler(MenuItemException mie){
        return mie.getMessage();
    }

    String menuItemPriceNotFoundHandler(MenuItemException mie) {
        return mie.getMessage();
    }
}
