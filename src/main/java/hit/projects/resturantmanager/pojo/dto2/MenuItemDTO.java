package hit.projects.resturantmanager.pojo.dto2;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import hit.projects.resturantmanager.pojo.MenuItem;
import lombok.Value;

@Value //  * Inject values to Spring-managed
@JsonPropertyOrder({"name", "price"})
public class MenuItemDTO {

    @JsonIgnore
    private MenuItem menuItem;

    public String getName() {return this.menuItem.getName(); }
    public int getPrice() { return this.menuItem.getPrice(); }

}
