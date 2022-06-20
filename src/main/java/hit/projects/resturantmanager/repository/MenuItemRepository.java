package hit.projects.resturantmanager.repository;

import hit.projects.resturantmanager.ENUMS.MenuCategories;
import hit.projects.resturantmanager.entity.MenuItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends MongoRepository<MenuItem,String> {

    Optional<MenuItem> getMenuItemByName(String name);
    boolean  existsByName(String name);
    List<MenuItem> findAllByPrice(int price);
    List<MenuItem> findAllByMenuCategories(MenuCategories menuCategories);

    void deleteByName(String name);

    Optional<MenuItem> findByName(String name);

}
