package hit.projects.resturantmanager.repository;

import hit.projects.resturantmanager.ENUMS.MenuCategories;
import hit.projects.resturantmanager.entity.MenuItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends MongoRepository<MenuItem,String> {

    MenuItem getMenuItemByName(String name);
    boolean  existsByName(String name);
    List<MenuItem> getMenuItemByPrice(int price);
    List<MenuItem> findAllByMenuCategories(MenuCategories menuCategories);

}
