package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.enums.MenuCategories;
import hit.projects.resturantmanager.pojo.MenuItem;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

public interface MenuItemService {

    ResponseEntity<CollectionModel<EntityModel<MenuItem>>> getMenu();



    EntityModel<MenuItem> getSingleMenuItem(String name);



    ResponseEntity<CollectionModel<EntityModel<MenuItem>>> getAllCategory(String category);

    EntityModel<MenuItem> updateMenuItem(String name, MenuItem menuItem);


    ResponseEntity<CollectionModel<EntityModel<MenuItem>>> getSingleMenuItemPrice(int price);

    EntityModel<MenuItem> newMenuItem(MenuItem menuItem);

    void deleteMenuItem(String name);

    CollectionModel<EntityModel<MenuItem>> getByCategoryAndPrice(int price, MenuCategories eCategory);
}
