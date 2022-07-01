package hit.projects.resturantmanager.pojo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import hit.projects.resturantmanager.pojo.Order;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
@JsonPropertyOrder({"orderList", "bill"})
public class OrderDTO {

    @JsonIgnore
    Order order;

    public Order getOrder() {
        return order;
    }

    public List<MenuItemDTO> getOrderList() {
        return order.getOrderList().stream().map(MenuItemDTO::new).collect(Collectors.toList());
    }
    public Double getBill() {return order.getBill();}
}
