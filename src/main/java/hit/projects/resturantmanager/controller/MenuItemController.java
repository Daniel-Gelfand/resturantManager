package hit.projects.resturantmanager.controller;

import hit.projects.resturantmanager.enums.MenuCategories;
import hit.projects.resturantmanager.pojo.MenuItem;
import hit.projects.resturantmanager.service.MenuItemService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/menu")
@RestController
@AllArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;

    @GetMapping()
    public ResponseEntity<CollectionModel<EntityModel<MenuItem>>> getMenu() {
        return menuItemService.getMenu();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<EntityModel<MenuItem>> getSingleMenuItem(@PathVariable String name){
        return menuItemService.getSingleMenuItem(name);
    }

    @GetMapping("/price/{price}")
    public ResponseEntity<CollectionModel<EntityModel<MenuItem>>> getSingleMenuItemByPrice(@PathVariable int price){
        return menuItemService.getSingleMenuItemPrice(price);
    }


    @GetMapping("/category/{category}")
    public ResponseEntity<CollectionModel<EntityModel<MenuItem>>> getAllCategory(@PathVariable String category) {
        return menuItemService.getAllCategory(category);
    }

    @PutMapping("/update/{name}")
    public ResponseEntity<EntityModel<MenuItem>> updateMenuItem(@PathVariable String name, @RequestBody MenuItem menuItem) {
        return menuItemService.updateMenuItem(name, menuItem);
    }


    @PostMapping
    public ResponseEntity<EntityModel<MenuItem>> newMenuItem(@RequestBody MenuItem menuItem) {
        return menuItemService.newMenuItem(menuItem);
    }


    @DeleteMapping("/delete/{name}")
    public ResponseEntity<?>  deleteMenuItem(@PathVariable String name){
        return menuItemService.deleteMenuItem(name);
    }


    @GetMapping("/search")
    public CollectionModel<EntityModel<MenuItem>> getByCategoryAndPrice(@RequestParam(defaultValue = "13") int price , @RequestParam MenuCategories category){
        return menuItemService.getByCategoryAndPrice(price,category);
    }


}
