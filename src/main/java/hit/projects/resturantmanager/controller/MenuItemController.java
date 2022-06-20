package hit.projects.resturantmanager.controller;

import hit.projects.resturantmanager.entity.MenuItem;
import hit.projects.resturantmanager.service.MenuItemService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
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
        System.out.println(price);
        return menuItemService.getSingleMenuItemPrice(price);
    }


    @GetMapping("/category/{category}")
    public List<MenuItem> getAllCategory(@PathVariable String category) {
        return menuItemService.getAllCategory(category);
    }

    @GetMapping("/{id}")
    public MenuItem getMenuItem(@PathVariable Long id) {
        return menuItemService.getMenuItem(id);
    }

    @PutMapping("/update/{name}")
    public MenuItem updateMenuItem(@PathVariable String name, @RequestBody MenuItem menuItem) {
        return menuItemService.updateMenuItem(name, menuItem);
    }

    /**
     * Add new menu item to DB.
     * @param menuItem
     * @return
     */
    @PostMapping
    public ResponseEntity<EntityModel<MenuItem>> newMenuItem(@RequestBody MenuItem menuItem) {
        return menuItemService.newMenuItem(menuItem);
    }


}
