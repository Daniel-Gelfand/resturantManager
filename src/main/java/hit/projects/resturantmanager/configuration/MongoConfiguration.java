package hit.projects.resturantmanager.configuration;

import hit.projects.resturantmanager.enums.MenuCategories;
import hit.projects.resturantmanager.enums.TableStatus;
import hit.projects.resturantmanager.pojo.MenuItem;
import hit.projects.resturantmanager.pojo.Order;
import hit.projects.resturantmanager.pojo.Table;
import hit.projects.resturantmanager.pojo.Waiter;
import hit.projects.resturantmanager.repository.MenuItemRepository;
import hit.projects.resturantmanager.repository.OrderRepository;
import hit.projects.resturantmanager.repository.TableRepository;
import hit.projects.resturantmanager.repository.WaiterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@Slf4j
public class MongoConfiguration {

    @Bean
    CommandLineRunner runner(MenuItemRepository myMenu, WaiterRepository waiterRepository, OrderRepository orderRepository, TableRepository tableRepository){
        return args -> {

            setMenuItemBean(myMenu);
            setWaiterItemBean(waiterRepository);
            setTables(tableRepository);

            setOrder(orderRepository, tableRepository, myMenu);

            Table table = tableRepository.getTableByTableNumber(1);
            log.info(String.valueOf(table));
            table.getOrderList().forEach(order -> log.info(String.valueOf(order.getBill())));
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

    private void setOrder(OrderRepository orderRepository, TableRepository tableRepository, MenuItemRepository menuItemRepository) {
        try {
            int tableNumber = 1;
            Table table = tableRepository.getTableByTableNumber(tableNumber);
            List<MenuItem> orderList = menuItemRepository.findAll();


            Order order1 = Order.builder().orderNumber(1).orderStatus(true).orderList(orderList).tableId(table.getId()).build();
            order1.setOrderList(orderList);
            orderList.forEach(menuItem -> {
                order1.setBill(order1.getBill() + menuItem.getPrice());
            });

            Order order2 = Order.builder().orderNumber(2).orderStatus(true).orderList(orderList).tableId(table.getId()).build();
            order2.setOrderList(orderList);
            orderList.forEach(menuItem -> {
                order2.setBill(order1.getBill() + menuItem.getPrice());
            });

            Order order3 = Order.builder().orderNumber(3).orderStatus(true).orderList(orderList).tableId(table.getId()).build();
            order3.setOrderList(orderList);
            orderList.forEach(menuItem -> {
                order3.setBill(order3.getBill() + menuItem.getPrice());

            });

            orderRepository.deleteAll();
            orderRepository.insert(List.of(order1, order2, order3));
            table.setTableStatus(TableStatus.BUSY);
            ArrayList<Order> orders = new ArrayList<>(Arrays.asList(order1,order2,order3));
            table.setOrderList(orders);
            System.out.println(table);
            tableRepository.save(table);
        } catch (Exception err) {

        }
    }

    public void setTables(TableRepository tableRepository) {
        Table table1 = Table.builder().tableNumber(1).tableStatus(TableStatus.AVAILABLE).build();
        Table table2 = Table.builder().tableNumber(2).tableStatus(TableStatus.AVAILABLE).build();
        Table table3 = Table.builder().tableNumber(3).tableStatus(TableStatus.AVAILABLE).build();
        Table table4 = Table.builder().tableNumber(4).tableStatus(TableStatus.AVAILABLE).build();

        tableRepository.deleteAll();
        tableRepository.insert(List.of(table1, table2, table3, table4));
    }
}
