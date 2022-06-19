package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.entity.MenuItem;

import java.util.List;

public interface MenuItemService {

    List<MenuItem> getMenu();

    MenuItem getMenuItem(Long id);

    MenuItem getSingleMenuItem(String name);

    MenuItem getAllCategory(String category);

    MenuItem updateMenuItem(Long id, MenuItem menuItem);

    MenuItem addMenuItem(MenuItem menuItem);

    List<MenuItem> getSingleMenuItemPrice(int price);
}
