package hit.projects.resturantmanager.utils;

import hit.projects.resturantmanager.pojo.MenuItem;
import hit.projects.resturantmanager.pojo.response.BitcoinResponseEntity;
import hit.projects.resturantmanager.pojo.response.DessertsResponseEntity;
import hit.projects.resturantmanager.pojo.response.PizzaResponseEntity;

import java.util.List;

public interface ResponseEntityConvertor {
    List<MenuItem> convertPizzas(List<PizzaResponseEntity> pizzaResponseEntity);

    List<MenuItem> convertDesserts(List<DessertsResponseEntity> dessertsResponseEntity);

    Double convertBTCRate(List<BitcoinResponseEntity> bitcoinResponseEntities);
}
