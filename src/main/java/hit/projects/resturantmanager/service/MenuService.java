package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.entity.Menu;
import hit.projects.resturantmanager.entity.MenuItem;

public interface MenuService {

    Menu getMenu();

    MenuItem getMenuItem(Long id);

    MenuItem getAllCategory(String category);

    MenuItem updateMenuItem(Long id, MenuItem menuItem);

    MenuItem addMenuItem(MenuItem menuItem);
}
