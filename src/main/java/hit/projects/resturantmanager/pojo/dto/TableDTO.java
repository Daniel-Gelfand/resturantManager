package hit.projects.resturantmanager.pojo.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import hit.projects.resturantmanager.enums.TableStatus;
import hit.projects.resturantmanager.pojo.Table;
import lombok.Value;

@Value
@JsonPropertyOrder({"tableStatus,tableNumber"})
public class TableDTO {


    @JsonIgnore
    private Table table;


    public int getTableNumber(){
        return this.table.getTableNumber();
    }

    public TableStatus getTableStatus(){
        return this.table.getTableStatus();
    }
}
