package hit.projects.resturantmanager.repository;

import hit.projects.resturantmanager.enums.MenuCategories;
import hit.projects.resturantmanager.pojo.MenuItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends MongoRepository<MenuItem, String> {

    Optional<MenuItem> getMenuItemByName(String name);

    boolean existsByName(String name);

    List<MenuItem> findAllByPrice(int price);

    List<MenuItem> findAllByMenuCategories(MenuCategories menuCategories);

    boolean existsByMenuCategories(MenuCategories menuCategories);

    void deleteByName(String name);

    Optional<MenuItem> findByName(String name);

    List<MenuItem> findAllByPriceLessThanAndMenuCategoriesEquals(int price, MenuCategories menuCategories);

}
