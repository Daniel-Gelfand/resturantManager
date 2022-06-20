package hit.projects.resturantmanager.exception;

public class MenuItemException extends RuntimeException{



    public MenuItemException(String name) {
        super("There are not Menuitem corresponding to name = " + name);
    }
    public MenuItemException(int price) {
        super("There are not Menuitem corresponding to name = " + price);
    }
}
