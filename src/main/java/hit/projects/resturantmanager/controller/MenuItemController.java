package hit.projects.resturantmanager.controller;

import hit.projects.resturantmanager.enums.MenuCategories;
import hit.projects.resturantmanager.pojo.MenuItem;
import hit.projects.resturantmanager.pojo.dto.MenuItemDTO;
import hit.projects.resturantmanager.service.MenuItemService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/menu")
@RestController
public class MenuItemController {

    private MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    /**
     *
     * @return
     */
    @GetMapping()
    public ResponseEntity<CollectionModel<EntityModel<MenuItem>>> getMenu() {
        return ResponseEntity.ok().body(menuItemService.getMenu());
    }

    /**
     *
     * @param price
     * @return
     */
    @GetMapping("/price/{price}")
    public ResponseEntity<CollectionModel<EntityModel<MenuItem>>> getSingleMenuItemByPrice(@PathVariable int price) {
        return ResponseEntity.ok().body(menuItemService.getSingleMenuItemPrice(price));
    }

    /**
     *
     * @param category
     * @return
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<CollectionModel<EntityModel<MenuItem>>> getAllCategory(@PathVariable String category) {
        return ResponseEntity.ok().body(menuItemService.getAllCategory(category));
    }

    /**
     *
     * @param price
     * @param category
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<CollectionModel<EntityModel<MenuItem>>> getByCategoryAndPrice(@RequestParam(defaultValue = "13") int price, @RequestParam MenuCategories category) {
        return ResponseEntity.ok().body(menuItemService.getByCategoryAndPrice(price, category));
    }

    /**
     *
     * @param name
     * @return
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<EntityModel<MenuItem>> getSingleMenuItem(@PathVariable String name) {
        return ResponseEntity.ok().body(menuItemService.getSingleMenuItem(name));
    }

    /**
     *
     * @param name
     * @return
     */
    @GetMapping("/{name}/info")
    public ResponseEntity<EntityModel<MenuItemDTO>> menuItemInfo(@PathVariable String name) {
        return ResponseEntity.ok().body(menuItemService.getMenuItemInfo(name));
    }

    /**
     *
     * @return
     */
    @GetMapping("/info")
    public ResponseEntity<CollectionModel<EntityModel<MenuItemDTO>>> allMenuItemInfo() {
        return ResponseEntity.ok().body(menuItemService.getAllMenuItemInfo());
    }

    /**
     *
     * @param name
     * @param menuItem
     * @return
     */
    @PutMapping("/update/{name}")
    public ResponseEntity<EntityModel<MenuItem>> updateMenuItem(@PathVariable String name, @RequestBody MenuItem menuItem) {
        return ResponseEntity.ok(menuItemService.updateMenuItem(name, menuItem));
    }

    /**
     *
     * @param menuItem
     * @return
     */
    @PostMapping
    public ResponseEntity<EntityModel<MenuItem>> newMenuItem(@RequestBody MenuItem menuItem) {
        return new ResponseEntity<>(menuItemService.newMenuItem(menuItem), HttpStatus.CREATED);
    }

    /**
     *
     * @param name
     * @return
     */
    @DeleteMapping("/delete/{name}")
    public ResponseEntity<?> deleteMenuItem(@PathVariable String name) {
        menuItemService.deleteMenuItem(name);
        return ResponseEntity.status(202).build();
    }
}
