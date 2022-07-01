package hit.projects.resturantmanager.utils;

import hit.projects.resturantmanager.pojo.DessertsResponseEntity;
import hit.projects.resturantmanager.pojo.MenuItem;
import hit.projects.resturantmanager.pojo.PizzaResponseEntity;

import java.util.List;

public interface ResponseEntityConvertor {
    List<MenuItem> convertPizzas(List<PizzaResponseEntity> pizzaResponseEntity);
    List<MenuItem> convertDesserts(List<DessertsResponseEntity> dessertsResponseEntity);
}
