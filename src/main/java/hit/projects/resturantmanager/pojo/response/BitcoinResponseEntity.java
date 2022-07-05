package hit.projects.resturantmanager.pojo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hit.projects.resturantmanager.configuration.BTC.Bpi;
import hit.projects.resturantmanager.configuration.BTC.Usd;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;

@Data
//@JsonIgnoreProperties
public class BitcoinResponseEntity {

    //TODO :: YARIN WATCH HERE

    //private Object time;
    //private String disclaimer;
    //private String chartName;
    private LinkedHashMap<String, Usd> bpi;

}
