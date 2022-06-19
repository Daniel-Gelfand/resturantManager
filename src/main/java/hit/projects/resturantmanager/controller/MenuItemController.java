package hit.projects.resturantmanager.controller;

import hit.projects.resturantmanager.entity.MenuItem;
import hit.projects.resturantmanager.service.MenuItemService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/menu")
@RestController
@AllArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;



    @GetMapping()
    public List<MenuItem> getMenu() {
        return menuItemService.getMenu();
    }



    @GetMapping("/name/{name}")
    public MenuItem getSingleMenuItem(@PathVariable String name){
        return menuItemService.getSingleMenuItem(name);
    }

    @GetMapping("/price/{price}")
    public List<MenuItem> getSingleMenuItemByPrice(@PathVariable int price){
        return menuItemService.getSingleMenuItemPrice(price);
    }


    @GetMapping("/{id}")
    public MenuItem getMenuItem(@PathVariable Long id) {
        return menuItemService.getMenuItem(id);
    }

    @GetMapping("/category/{category}")
    public List<MenuItem> getAllCategory(@PathVariable String category) {
        return menuItemService.getAllCategory(category);
    }

    @PutMapping("/{id}")
    public MenuItem updateMenuItem(@PathVariable Long id, @RequestBody MenuItem menuItem) {
        return menuItemService.updateMenuItem(id, menuItem);
    }



    @PostMapping
    public MenuItem addMenuItem(@RequestBody MenuItem menuItem) {
        return menuItemService.addMenuItem(menuItem);
    }


}
