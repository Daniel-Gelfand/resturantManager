package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.entity.Menu;
import hit.projects.resturantmanager.entity.MenuItem;
import hit.projects.resturantmanager.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService{

    private MenuRepository menuRepository;

    @Autowired
    public MenuServiceImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public Menu getMenu() {
        return null;
    }

    @Override
    public MenuItem getMenuItem(Long id) {
        return null;
    }

    @Override
    public MenuItem getAllCategory(String category) {
        return null;
    }

    @Override
    public MenuItem updateMenuItem(Long id, MenuItem menuItem) {
        return null;
    }

    @Override
    public MenuItem addMenuItem(MenuItem menuItem) {
        return null;
    }
}
