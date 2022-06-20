package hit.projects.resturantmanager.exception;

public class MenuItemException extends RuntimeException{


    public MenuItemException(String name) {
        super("There are not Menuitem corresponding to name = " + name);
    }
}
