package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.ENUMS.MenuCategories;
import hit.projects.resturantmanager.entity.MenuItem;
import hit.projects.resturantmanager.repository.MenuItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {

    private MenuItemRepository menuItemRepository;

    @Override
    public List<MenuItem> getMenu() {
        return menuItemRepository.findAll();
    }

    @Override
    public MenuItem getMenuItem(Long id) {
        return null;
    }

    @Override
    public MenuItem getSingleMenuItem(String name) {
        return menuItemRepository.getMenuItemByName(name);
    }

    @Override
    public List<MenuItem> getAllCategory(String category) {
        return menuItemRepository.findAllByMenuCategories(MenuCategories.valueOf(category.toUpperCase()));
    }

    @Override
    public MenuItem updateMenuItem(Long id, MenuItem menuItem) {
        return null;
    }

    @Override
    public MenuItem addMenuItem(MenuItem menuItem) {
        return menuItemRepository.insert(menuItem);
    }

    @Override
    public List<MenuItem> getSingleMenuItemPrice(int price) {
        return menuItemRepository.getMenuItemByPrice(price);
    }
}
