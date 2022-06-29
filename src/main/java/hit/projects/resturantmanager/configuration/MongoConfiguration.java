package hit.projects.resturantmanager.configuration;

import hit.projects.resturantmanager.enums.MenuCategories;
import hit.projects.resturantmanager.enums.TableStatus;
import hit.projects.resturantmanager.pojo.*;
import hit.projects.resturantmanager.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

@Configuration
@Slf4j
public class MongoConfiguration {

    @Bean
    CommandLineRunner runner(MenuItemRepository myMenu, WaiterRepository waiterRepository, OrderRepository orderRepository, TableRepository tableRepository, ManagerRepository managerRepository){
        return args -> {

            setMenuItemBean(myMenu);
            setWaiterItemBean(waiterRepository, tableRepository);
            setTables(tableRepository, waiterRepository);
            setManagers(managerRepository);
            setOrder(orderRepository, tableRepository, myMenu);

            log.info(String.valueOf(LocalDate.of(2002, Month.APRIL, 1)));

        };
    }

    private void setManagers(ManagerRepository managerRepository){
        Manager manager1 = new Manager(209381773,"Gilad","Shalit",8000.0,true);
        Manager manager2 = new Manager(209222772,"Benjamin","Netanyahu",8000.0,false);
        Manager manager3 = new Manager(318324258,"Naftali","Benet",8000.0,false);
        Manager manager4 = new Manager(209444775,"Yakov","Ha-Hayat",8000.0,true);
        System.out.println(manager1);
        managerRepository.deleteAll();
        managerRepository.insert(List.of(manager1,manager2,manager3,manager4));
    }

    private void setMenuItemBean(MenuItemRepository myMenu) {
        MenuItem menuItem1 = new MenuItem("Steak Pargit", MenuCategories.MAINCOURSE,69);
        MenuItem menuItem2 = new MenuItem("IceCream", MenuCategories.DESSERT,25);
        MenuItem menuItem3 = new MenuItem("Coca-Cola", MenuCategories.DRINKS,12);
        MenuItem menuItem4 = new MenuItem("Carpaccio", MenuCategories.APPETIZER,33);
        MenuItem menuItem5 = new MenuItem("Sprite", MenuCategories.DRINKS,12);

        myMenu.deleteAll();
        myMenu.insert(List.of(menuItem1,menuItem2,menuItem3,menuItem4,menuItem5));

    }

    private void setWaiterItemBean(WaiterRepository waiterRepository, TableRepository tableRepository) {
        List<Table> tables = tableRepository.findAll();

        Waiter waiter1 = new Waiter(318324258, "Matan", "Bar", 3000.0, 0.0, true);
        waiter1.setTableList(tables);
        Waiter waiter2 = new Waiter(313324258, "Dani", "Gal", 3000.0, 0.0, true);
        waiter2.setTableList(tables);
        Waiter waiter3 = new Waiter(209324258, "Yarin", "Ben", 3000.0, 0.0, true);
        waiter3.setTableList(tables);
        Waiter waiter4 = new Waiter(208324258, "Matan", "Kor", 3000.0, 0.0, true);
        waiter4.setTableList(tables);

        waiterRepository.deleteAll();
        waiterRepository.insert(List.of(waiter1,waiter2,waiter3,waiter4));
    }

    private void setOrder(OrderRepository orderRepository, TableRepository tableRepository, MenuItemRepository menuItemRepository) {
        try {
            int tableNumber = 1;
            Table table = tableRepository.getTableByTableNumber(tableNumber).orElseThrow();
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

    public void setTables(TableRepository tableRepository, WaiterRepository waiterRepository) {
        List<Waiter> waiters = waiterRepository.findAllByOnDuty(true);

        Table table1 = Table.builder().tableNumber(1).tableStatus(TableStatus.AVAILABLE).build();
        table1.setWaitersList(waiters);
        Table table2 = Table.builder().tableNumber(2).tableStatus(TableStatus.AVAILABLE).build();
        table2.setWaitersList(waiters);
        Table table3 = Table.builder().tableNumber(3).tableStatus(TableStatus.AVAILABLE).build();
        table3.setWaitersList(waiters);
        Table table4 = Table.builder().tableNumber(4).tableStatus(TableStatus.AVAILABLE).build();
        table4.setWaitersList(waiters);

        tableRepository.deleteAll();
        tableRepository.insert(List.of(table1, table2, table3, table4));
    }
}
