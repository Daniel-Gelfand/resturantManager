package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.entity.MenuItem;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface MenuItemService {

    ResponseEntity<CollectionModel<EntityModel<MenuItem>>> getMenu();



    ResponseEntity<EntityModel<MenuItem>> getSingleMenuItem(String name);



    ResponseEntity<CollectionModel<EntityModel<MenuItem>>> getAllCategory(String category);

    ResponseEntity<EntityModel<MenuItem>> updateMenuItem(String name, MenuItem menuItem);


    ResponseEntity<CollectionModel<EntityModel<MenuItem>>> getSingleMenuItemPrice(int price);

    ResponseEntity<EntityModel<MenuItem>> newMenuItem(MenuItem menuItem);

    ResponseEntity<?> deleteMenuItem(String name);
}
