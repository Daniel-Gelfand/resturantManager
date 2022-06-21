package hit.projects.resturantmanager.exception;

import hit.projects.resturantmanager.pojo.MenuItem;

public class MenuItemException extends RuntimeException{



    public MenuItemException(String name) {
        super("There are not Menuitem corresponding to name = " + name);
    }
    public MenuItemException(int price) {
        super("There are not Menuitem corresponding to name = " + price);
    }
    public MenuItemException(MenuItem menuItem){
        super("There are problem with add new menuitem");
    }
}
