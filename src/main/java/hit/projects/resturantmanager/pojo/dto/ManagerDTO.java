package hit.projects.resturantmanager.pojo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import hit.projects.resturantmanager.pojo.Manager;
import lombok.Value;

@Value
@JsonPropertyOrder({"fullName","onDuty"})
public class ManagerDTO {

    @JsonIgnore
    private Manager manager;


    public String getFullName(){
        return String.format("%s %s", manager.getFirstName(), manager.getLastName());
    }


    public boolean isOnDuty(){
        return this.manager.isOnDuty();
    }

}
