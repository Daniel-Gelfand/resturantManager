package hit.projects.resturantmanager.controller;

import hit.projects.resturantmanager.enums.MenuCategories;
import hit.projects.resturantmanager.pojo.MenuItem;
import hit.projects.resturantmanager.pojo.dto.MenuItemDTO;
import hit.projects.resturantmanager.service.MenuItemService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;


@RequestMapping("/menu")
@RestController
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    /**
     * In this method get all items in the menu.
     * @return status 200, all items from database.
     */
    @GetMapping()
    public ResponseEntity<CollectionModel<EntityModel<MenuItem>>> getMenu() {
        return ResponseEntity.ok().body(menuItemService.getMenu());
    }

    /**
     * In this method we get single menu item by his price.
     * @param price -> Expect to get price of the item.
     * @return status 200, and the item by his price.
     */
    @GetMapping("/price/{price}")
    public ResponseEntity<CollectionModel<EntityModel<MenuItem>>> getSingleMenuItemByPrice(@PathVariable int price) {
        return ResponseEntity.ok().body(menuItemService.getSingleMenuItemPrice(price));
    }

    /**
     * In this method we get all items by specific category.
     * @param category -> Expect to get category name.
     * @return status 200, and the category items.
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<CollectionModel<EntityModel<MenuItem>>> getAllCategory(@PathVariable String category) {
        return ResponseEntity.ok().body(menuItemService.getAllCategory(category));
    }

    /**
     * In this method get items by category and price.
     * @param price -> Expect to get price of the item.
     * @param category -> Expect to get category name.
     * @return status 200, and the item by category and price.
     */
    @GetMapping("/search")
    public ResponseEntity<CollectionModel<EntityModel<MenuItem>>> getByCategoryAndPrice(@RequestParam(defaultValue = "13") int price, @RequestParam MenuCategories category) {
        return ResponseEntity.ok().body(menuItemService.getByCategoryAndPrice(price, category));
    }

    /**
     * In this method we get single menu item by name.
     * @param name -> Expect to get name of the item.
     * @return status 200, and the item by his name.
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<EntityModel<MenuItem>> getSingleMenuItem(@NotBlank @Validated @PathVariable String name) {
        return ResponseEntity.ok().body(menuItemService.getSingleMenuItem(name));
    }

    /**
     * In this method we get info about specific item. (DTO)
     * @param name -> Expect to get name of the item.
     * @return status 200, and the item by his name.
     */
    @GetMapping("/{name}/info")
    public ResponseEntity<EntityModel<MenuItemDTO>> menuItemInfo(@PathVariable String name) {
        return ResponseEntity.ok().body(menuItemService.getMenuItemInfo(name));
    }

    /**
     * In this method we get specific info about all items in the menu. (DTO)
     * @return status 200, and the items from database.
     */
    @GetMapping("/info")
    public ResponseEntity<CollectionModel<EntityModel<MenuItemDTO>>> allMenuItemInfo() {
        return ResponseEntity.ok().body(menuItemService.getAllMenuItemInfo());
    }

    /**
     * In this method we update item by his name.
     * @param name -> Expect to get name of the item.
     * @param menuItem -> Expect to json with menuItem details.
     * @return status 201
     */
    @PutMapping("/update/{name}")
    public ResponseEntity<EntityModel<MenuItem>> updateMenuItem(@PathVariable String name, @RequestBody MenuItem menuItem) {
        return ResponseEntity.ok(menuItemService.updateMenuItem(name, menuItem));
    }

    /**
     *  In this method we create new menu item in the database
     * @param menuItem -> Expect to json with menuItem details.
     * @return status 201, create new item in the database.
     */
    @PostMapping
    public ResponseEntity<EntityModel<MenuItem>> newMenuItem(@NotBlank @Validated @RequestBody MenuItem menuItem) {
        return new ResponseEntity<>(menuItemService.newMenuItem(menuItem), HttpStatus.CREATED);
    }

    /**
     * In this method we delete the specific item from database.
     * @param name -> Expect to get the name of the item that we want to delete.
     * @return status 202, delete the item from database.
     */
    @DeleteMapping("/delete/{name}")
    public ResponseEntity<?> deleteMenuItem(@PathVariable String name) {
        menuItemService.deleteMenuItem(name);
        return ResponseEntity.status(202).build();
    }
}
