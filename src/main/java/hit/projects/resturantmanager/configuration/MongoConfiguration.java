package hit.projects.resturantmanager.configuration;

import hit.projects.resturantmanager.enums.MenuCategories;
import hit.projects.resturantmanager.enums.TableStatus;
import hit.projects.resturantmanager.exception.RestaurantGeneralException;
import hit.projects.resturantmanager.pojo.*;
import hit.projects.resturantmanager.pojo.response.DessertsResponseEntity;
import hit.projects.resturantmanager.pojo.response.PizzaResponseEntity;
import hit.projects.resturantmanager.repository.*;
import hit.projects.resturantmanager.utils.Constant;
import hit.projects.resturantmanager.utils.ResponseEntityConvertor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Configuration
@Slf4j
public class MongoConfiguration {

    @Value("${app.foodUrl}")
    private String foodUrl;


    //TODO: what about this static method ?
    @Bean
    public RestTemplate restTemplate() {
        final RestTemplate restTemplate = new RestTemplate();

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);

        return restTemplate;
    }


    @Bean
    CommandLineRunner runner(MenuItemRepository myMenu, WaiterRepository waiterRepository,
                             OrderRepository orderRepository, TableRepository tableRepository,
                             ManagerRepository managerRepository, RestTemplate restTemplate,
                             ResponseEntityConvertor responseEntityConvertor) {
        return args -> {

            managerRepository.deleteAll();
            waiterRepository.deleteAll();
            tableRepository.deleteAll();
            orderRepository.deleteAll();




            setMenuItemBean(myMenu, restTemplate, responseEntityConvertor);
            setWaiterItemBean(waiterRepository, tableRepository);
            setTables(tableRepository, waiterRepository);
            setManagers(managerRepository);
            setOrder(orderRepository, tableRepository, myMenu);

            log.info(String.valueOf(LocalDate.of(2002, Month.APRIL, 1)));

        };
    }

    private void setManagers(ManagerRepository managerRepository) {

        Manager manager1 = new Manager(209381773, "Gilad", "Shalit", 8000.0, true);
        Manager manager2 = new Manager(209222772, "Benjamin", "Netanyahu", 8000.0, false);
        Manager manager3 = new Manager(318324258, "Naftali", "Benet", 8000.0, false);
        Manager manager4 = new Manager(209444775, "Yakov", "Ha-Hayat", 8000.0, true);
        managerRepository.insert(List.of(manager1, manager2, manager3, manager4));
    }

    private void setMenuItemBean(MenuItemRepository myMenu, RestTemplate restTemplate,
                                 ResponseEntityConvertor responseEntityConvertor) {
        try {
            //CompletableFuture<List<MenuItem>> pizzas = getPizzas(restTemplate, responseEntityConvertor);
            //CompletableFuture<List<MenuItem>> desserts = getDesserts(restTemplate, responseEntityConvertor);

            MenuItem menuItem1 = new MenuItem("Steak Pargit", MenuCategories.MAINCOURSE, 69);
            MenuItem menuItem2 = new MenuItem("IceCream", MenuCategories.DESSERT, 25);
            MenuItem menuItem3 = new MenuItem("Coca-Cola", MenuCategories.DRINKS, 12);
            MenuItem menuItem4 = new MenuItem("Carpaccio", MenuCategories.APPETIZER, 33);
            MenuItem menuItem5 = new MenuItem("Sprite", MenuCategories.DRINKS, 12);



            myMenu.insert(List.of(menuItem1, menuItem2, menuItem3, menuItem4, menuItem5));
            //myMenu.insert(pizzas.get());
            //myMenu.insert(desserts.get());
        } catch (Exception e) {
            log.error("setMenuItemBean", "message: problem with fetching food");
        }
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

        waiterRepository.insert(List.of(waiter1, waiter2, waiter3, waiter4));
    }

    private void setOrder(OrderRepository orderRepository, TableRepository tableRepository, MenuItemRepository menuItemRepository) {
        try {

            int tableNumber = 1;
            Table table = tableRepository.getTableByTableNumber(tableNumber).orElseThrow();
            List<MenuItem> orderList = menuItemRepository.findAll();


            Order order1 = Order.builder().orderNumber(1).isBillPaid(false).orderList(orderList).tableNumber(table.getTableNumber()).build();
            order1.setOrderList(orderList);
            orderList.forEach(menuItem -> {
                order1.setBill(order1.getBill() + menuItem.getPrice());
            });

            Order order2 = Order.builder().orderNumber(2).isBillPaid(false).orderList(orderList).tableNumber(table.getTableNumber()).build();
            order2.setOrderList(orderList);
            orderList.forEach(menuItem -> {
                order2.setBill(order1.getBill() + menuItem.getPrice());
            });

            Order order3 = Order.builder().orderNumber(3).isBillPaid(false).orderList(orderList).tableNumber(table.getTableNumber()).build();
            order3.setOrderList(orderList);
            orderList.forEach(menuItem -> {
                order3.setBill(order3.getBill() + menuItem.getPrice());

            });

            orderRepository.insert(List.of(order1, order2, order3));
            table.setTableStatus(TableStatus.BUSY);
            ArrayList<Order> orders = new ArrayList<>(Arrays.asList(order1, order2, order3));
            table.setOrderList(orders);
            tableRepository.save(table);
        } catch (Exception err) {

        }
    }

    private void setTables(TableRepository tableRepository, WaiterRepository waiterRepository) {

        List<Waiter> waiters = waiterRepository.findAllByOnDuty(true);

        Table table1 = Table.builder().tableNumber(1).tableStatus(TableStatus.AVAILABLE).build();
        table1.setWaitersList(waiters);
        Table table2 = Table.builder().tableNumber(2).tableStatus(TableStatus.AVAILABLE).build();
        table2.setWaitersList(waiters);
        Table table3 = Table.builder().tableNumber(3).tableStatus(TableStatus.AVAILABLE).build();
        table3.setWaitersList(waiters);
        Table table4 = Table.builder().tableNumber(4).tableStatus(TableStatus.AVAILABLE).build();
        table4.setWaitersList(waiters);

        tableRepository.insert(List.of(table1, table2, table3, table4));
    }

    @Async
    public CompletableFuture<List<MenuItem>> getDesserts(RestTemplate restTemplate,
                                                         ResponseEntityConvertor responseEntityConvertor) {
        try {
            final HttpEntity<String> entity = getHeaders();

            ResponseEntity<DessertsResponseEntity[]> response =
                    restTemplate.exchange(foodUrl + "/desserts", HttpMethod.GET, entity, DessertsResponseEntity[].class);

            if (response.getStatusCode() == HttpStatus.OK) {
                log.info("getDesserts", "message: Request desserts Successful.");
                return CompletableFuture
                        .completedFuture(responseEntityConvertor.convertDesserts(List.of(response.getBody())));
            }

            log.error("getDesserts", "message: Request desserts Failed with status = " + response.getStatusCode());
            throw new RestaurantGeneralException(Constant.FETCHING_ERROR_MESSAGE);
        } catch (Exception e) {
            log.error("addDesserts", "message: problem with fetching desserts API.");
            throw new RestaurantGeneralException(Constant.FETCHING_ERROR_MESSAGE);
        }
    }

    @Async
    public CompletableFuture<List<MenuItem>> getPizzas(RestTemplate restTemplate,
                                                       ResponseEntityConvertor responseEntityConvertor) {
        try {
            final HttpEntity<String> entity = getHeaders();

            ResponseEntity<PizzaResponseEntity[]> response =
                    restTemplate.exchange(foodUrl + "/pizzas", HttpMethod.GET, entity, PizzaResponseEntity[].class);


            if (response.getStatusCode() == HttpStatus.OK) {
                log.info("getPizzas", "message: Request pizzas Successful.");
                return CompletableFuture
                        .completedFuture(responseEntityConvertor.convertPizzas(List.of(response.getBody())));
            }

            log.error("getPizzas", "message: Request pizzas Failed with status = " + response.getStatusCode());
            throw new RestaurantGeneralException(Constant.FETCHING_ERROR_MESSAGE);
        } catch (Exception e) {
            log.error("addPizzas", "message: problem with fetching pizzas API.");
            throw new RestaurantGeneralException(Constant.FETCHING_ERROR_MESSAGE);
        }
    }


    private HttpEntity<String> getHeaders() {
        final HttpHeaders headers = new HttpHeaders();

        headers.set("X-RapidAPI-Key", "3acb74cfe6mshe9000fa87a80359p1d86b8jsnb4a1daf4a027");
        headers.set("X-RapidAPI-Host", "pizza-and-desserts.p.rapidapi.com");

        final HttpEntity<String> entity = new HttpEntity<>(headers);
        return entity;
    }

}
