package hit.projects.resturantmanager.pojo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import hit.projects.resturantmanager.pojo.Manager;
import lombok.Value;

@Value
@JsonPropertyOrder({"firstName", "lastName","onDuty"})
public class ManagerDTO {

    @JsonIgnore
    private Manager manager;

    public String getFirstName() {
        return this.manager.getFirstName();
    }
    public String getLastName() {
        return this.manager.getLastName();
    }

    public boolean isOnDuty(){
        return this.manager.isOnDuty();
    }

}
