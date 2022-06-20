package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.entity.MenuItem;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MenuItemService {

    ResponseEntity<CollectionModel<EntityModel<MenuItem>>> getMenu();



    ResponseEntity<EntityModel<MenuItem>> getSingleMenuItem(String name);



    List<MenuItem> getAllCategory(String category);

    MenuItem updateMenuItem(String name, MenuItem menuItem);


    List<MenuItem> getSingleMenuItemPrice(int price);

    ResponseEntity<EntityModel<MenuItem>> newMenuItem(MenuItem menuItem);
}
