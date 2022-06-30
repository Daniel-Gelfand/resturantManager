package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.enums.MenuCategories;
import hit.projects.resturantmanager.pojo.MenuItem;
import hit.projects.resturantmanager.pojo.dto2.MenuItemDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

public interface MenuItemService {

    CollectionModel<EntityModel<MenuItem>> getMenu();

    CollectionModel<EntityModel<MenuItem>> getSingleMenuItemPrice(int price);

    CollectionModel<EntityModel<MenuItem>> getByCategoryAndPrice(int price, MenuCategories eCategory);

    CollectionModel<EntityModel<MenuItem>> getAllCategory(String category);

    EntityModel<MenuItem> newMenuItem(MenuItem menuItem);

    EntityModel<MenuItem> updateMenuItem(String name, MenuItem menuItem);

    EntityModel<MenuItem> getSingleMenuItem(String name);

    void deleteMenuItem(String name);

    EntityModel<MenuItemDTO> getMenuItemInfo(String name);

    CollectionModel<EntityModel<MenuItemDTO>> getAllMenuItemInfo();
}
