package hit.projects.resturantmanager.configuration;

import hit.projects.resturantmanager.ENUMS.MenuCategories;
import hit.projects.resturantmanager.entity.MenuItem;
import hit.projects.resturantmanager.entity.Waiter;
import hit.projects.resturantmanager.repository.MenuItemRepository;
import hit.projects.resturantmanager.repository.WaiterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Slf4j
public class MongoConfiguration {

    @Bean
    CommandLineRunner runner(MenuItemRepository myMenu, WaiterRepository waiterRepository){
        return args -> {

            setMenuItemBean(myMenu);

            setWaiterItemBean(waiterRepository);
        };
    }

    private void setMenuItemBean(MenuItemRepository myMenu) {
        MenuItem menuItem1 = new MenuItem("Steak Pargit", MenuCategories.MAINCOURSE,69);
        MenuItem menuItem2 = new MenuItem("IceCream", MenuCategories.DESSERT,25);
        MenuItem menuItem3 = new MenuItem("Coca-Cola", MenuCategories.DRINKS,12);
        MenuItem menuItem4 = new MenuItem("Carpaccio", MenuCategories.APPETIZER,33);
        MenuItem menuItem5 = new MenuItem("Sprite", MenuCategories.DRINKS,12);

        myMenu.deleteAll();
        myMenu.insert(List.of(menuItem1,menuItem2,menuItem3,menuItem4,menuItem5));

        String s = "drinks";
        //System.out.println(MenuCategories.valueOf(s.toUpperCase()));
        //.out.println(myMenu.findAllByMenuCategories(MenuCategories.valueOf(s.toUpperCase())));
        //System.out.println(myMenu.getMenuItemByPrice(69));
//        System.out.println(myMenu.findByName("IceCream"));
    }

    private void setWaiterItemBean(WaiterRepository waiterRepository) {

        Waiter waiter1 = new Waiter(318324258, "Matan", "Bar", 3000.0, 0.0, true);
        Waiter waiter2 = new Waiter(313324258, "Dani", "Gal", 3000.0, 0.0, true);
        Waiter waiter3 = new Waiter(209324258, "Yarin", "Ben", 3000.0, 0.0, true);
        Waiter waiter4 = new Waiter(208324258, "Matan", "Kor", 3000.0, 0.0, true);

        waiterRepository.deleteAll();
        waiterRepository.insert(List.of(waiter1,waiter2,waiter3,waiter4));
    }
}
