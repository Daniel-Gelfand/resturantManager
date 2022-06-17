package hit.projects.resturantmanager.controller;

import hit.projects.resturantmanager.entity.Menu;
import hit.projects.resturantmanager.entity.MenuItem;
import hit.projects.resturantmanager.entity.Waiter;
import hit.projects.resturantmanager.service.MenuService;
import hit.projects.resturantmanager.service.MenuServiceImpl;
import hit.projects.resturantmanager.service.WaiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/menu")
@RestController
public class MenuController  {

    private MenuService menuService;

    @Autowired
    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public Menu getMenu() {
        return menuService.getMenu();
    }

    @GetMapping("/{id}")
    public MenuItem getMenuItem(@PathVariable Long id) {
        return menuService.getMenuItem(id);
    }

    @GetMapping("/{category}")
    public MenuItem getAllCategory(@PathVariable String category) {
        return menuService.getAllCategory(category);
    }

    @PutMapping("/{id}")
    public MenuItem updateMenuItem(@PathVariable Long id, @RequestBody MenuItem menuItem) {
        return menuService.updateMenuItem(id, menuItem);
    }

    @PostMapping
    public MenuItem addMenuItem(@RequestBody MenuItem menuItem) {
        return menuService.addMenuItem(menuItem);
    }


}
