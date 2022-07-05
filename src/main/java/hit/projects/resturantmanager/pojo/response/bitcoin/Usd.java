package hit.projects.resturantmanager.pojo.response.bitcoin;

import lombok.Data;

@Data
public class Usd {
    private String code;
    private String symbol;
    private String rate;
    private String description;
    private Double rate_float;
}
