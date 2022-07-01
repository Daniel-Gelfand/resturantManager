package hit.projects.resturantmanager.utils;

import hit.projects.resturantmanager.enums.MenuCategories;
import hit.projects.resturantmanager.pojo.response.DessertsResponseEntity;
import hit.projects.resturantmanager.pojo.MenuItem;
import hit.projects.resturantmanager.pojo.response.PizzaResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ResponseEntityConvertorImpl implements ResponseEntityConvertor{

    @Override
    public List<MenuItem> convertPizzas(List<PizzaResponseEntity> pizzaResponseEntity) {
        return pizzaResponseEntity.stream()
                .map(currentPizzaResponseEntity ->
                        MenuItem.builder()
                                .menuCategories(MenuCategories.MAINCOURSE)
                                .price(currentPizzaResponseEntity.getPrice())
                                .name(currentPizzaResponseEntity.getName())
                                .build()).collect(Collectors.toList());
    }

    @Override
    public List<MenuItem> convertDesserts(List<DessertsResponseEntity> dessertsResponseEntity) {
        return dessertsResponseEntity.stream()
                .map(currentDessertsResponseEntity ->
                        MenuItem.builder()
                                .menuCategories(MenuCategories.DESSERT)
                                .price(currentDessertsResponseEntity.getPrice())
                                .name(currentDessertsResponseEntity.getName())
                                .build()).collect(Collectors.toList());
    }
}
