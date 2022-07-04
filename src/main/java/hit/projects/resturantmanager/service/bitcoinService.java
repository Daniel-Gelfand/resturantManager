//package hit.projects.resturantmanager.service;
//

import hit.projects.resturantmanager.pojo.response.BitcoinResponseEntity;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

//@Service
//public class bitcoinService {
//
//
//    private RestTemplate restTemplate;
//
//    public bitcoinService(RestTemplateBuilder restTemplateBuilder) {
//        this.restTemplate = restTemplateBuilder.build();
//    }
//
//    @Async
//    public CompletableFuture<BitcoinResponseEntity> bitcoinDetails(){
//        String urlTemplate = "https://api.coindesk.com/v1/bpi/currentprice.json";
//
//        BitcoinResponseEntity btc = this.restTemplate.getForObject(urlTemplate,BitcoinResponseEntity.class);
//
//        return CompletableFuture.completedFuture(btc);
//
//
//    }
//
//}
