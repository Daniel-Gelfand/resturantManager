package hit.projects.resturantmanager.configuration;

import hit.projects.resturantmanager.ENUMS.MenuCategories;
import hit.projects.resturantmanager.entity.MenuItem;
import hit.projects.resturantmanager.repository.MenuItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Slf4j
public class MongoConfiguration {

    @Bean
    CommandLineRunner runner(MenuItemRepository myMenu){
        return args -> {

            MenuItem menuItem1 = new MenuItem("Steak Pargit", MenuCategories.MAINCOURSE,69);
            MenuItem menuItem2 = new MenuItem("IceCream", MenuCategories.DESSERT,25);
            MenuItem menuItem3 = new MenuItem("Coca-Cola", MenuCategories.DRINKS,12);
            MenuItem menuItem4 = new MenuItem("Carpaccio", MenuCategories.APPETIZER,33);

            myMenu.deleteAll();
            myMenu.insert(List.of(menuItem1,menuItem2,menuItem3,menuItem4));

            String s = "drinks";
            System.out.println(MenuCategories.valueOf(s.toUpperCase()));
            System.out.println(myMenu.findAllByMenuCategories(MenuCategories.valueOf(s.toUpperCase())));
            System.out.println(myMenu.getMenuItemByPrice(69));

        };
    }

}
