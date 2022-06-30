package hit.projects.resturantmanager.controller;

import hit.projects.resturantmanager.enums.MenuCategories;
import hit.projects.resturantmanager.pojo.MenuItem;
import hit.projects.resturantmanager.pojo.dto2.MenuItemDTO;
import hit.projects.resturantmanager.service.MenuItemService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@RequestMapping("/menu")
@RestController
@AllArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;

    @GetMapping()
    public ResponseEntity<CollectionModel<EntityModel<MenuItem>>> getMenu() {
        return ResponseEntity.ok().body(menuItemService.getMenu());
    }

    @GetMapping("/price/{price}")
    public ResponseEntity<CollectionModel<EntityModel<MenuItem>>> getSingleMenuItemByPrice(@PathVariable int price){
        return ResponseEntity.ok().body(menuItemService.getSingleMenuItemPrice(price));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<CollectionModel<EntityModel<MenuItem>>> getAllCategory(@PathVariable String category) {
        return ResponseEntity.ok().body(menuItemService.getAllCategory(category));
    }

    @GetMapping("/search")
    public ResponseEntity<CollectionModel<EntityModel<MenuItem>>> getByCategoryAndPrice(@RequestParam(defaultValue = "13") int price , @RequestParam MenuCategories category){
        return ResponseEntity.ok().body(menuItemService.getByCategoryAndPrice(price,category));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<EntityModel<MenuItem>> getSingleMenuItem(@PathVariable String name){
        return ResponseEntity.ok().body(menuItemService.getSingleMenuItem(name));
    }

    @PutMapping("/update/{name}")
    public ResponseEntity<EntityModel<MenuItem>> updateMenuItem(@PathVariable String name, @RequestBody MenuItem menuItem) {
        return ResponseEntity.ok(menuItemService.updateMenuItem(name, menuItem));
    }

    @PostMapping
    public ResponseEntity<EntityModel<MenuItem>> newMenuItem(@RequestBody MenuItem menuItem) {
        return new ResponseEntity<>(menuItemService.newMenuItem(menuItem),HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<?>  deleteMenuItem(@PathVariable String name){
        menuItemService.deleteMenuItem(name);
        return ResponseEntity.status(202).build();
    }

    @GetMapping("/{name}/info")
    public ResponseEntity<EntityModel<MenuItemDTO>> menuItemInfo(@PathVariable String name) {
        return ResponseEntity.ok().body(menuItemService.getMenuItemInfo(name));
    }

    @GetMapping("/info")
    public ResponseEntity<CollectionModel<EntityModel<MenuItemDTO>>> allMenuItemInfo() {
        return ResponseEntity.ok().body(menuItemService.getAllMenuItemInfo());
    }
}
