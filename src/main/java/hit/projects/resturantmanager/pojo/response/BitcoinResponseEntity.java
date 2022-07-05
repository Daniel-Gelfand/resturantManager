package hit.projects.resturantmanager.pojo.response;

import hit.projects.resturantmanager.pojo.response.bitcoin.Usd;
import lombok.Data;

import java.util.LinkedHashMap;

@Data
public class BitcoinResponseEntity {

    private LinkedHashMap<String, Usd> bpi;

}
