package hit.projects.resturantmanager.exception;

public class MenuItemNotFoundException extends RuntimeException{



    public MenuItemNotFoundException(String name) {
        super("There are not Menuitem corresponding to name = " + name);
    }
}
