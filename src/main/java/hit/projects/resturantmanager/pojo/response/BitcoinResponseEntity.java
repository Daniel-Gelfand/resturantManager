package hit.projects.resturantmanager.pojo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties
public class BitcoinResponseEntity {

    //TODO :: YARIN WATCH HERE

    private Object time;
    private Object disclaimer;
    private Object chartName;
    private Object bpi;


}
