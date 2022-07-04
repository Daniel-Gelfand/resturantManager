package hit.projects.resturantmanager.pojo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import hit.projects.resturantmanager.pojo.Waiter;
import lombok.Value;

@Value
@JsonPropertyOrder({"fullName", "onDuty"})
public class WaiterDTO {

    @JsonIgnore
    Waiter waiter;

    public Waiter getWaiter() {
        return waiter;
    }

    public String getFullName() {
        return String.format("%s %s", waiter.getFirstName(), waiter.getLastName());
    }

    public boolean isOnDuty() {
        return waiter.isOnDuty();
    }
}
