package hit.projects.resturantmanager.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Payment {
    @NotNull(message = "test")
    Integer orderNumber;
    @NotNull
    Integer payment;
}
