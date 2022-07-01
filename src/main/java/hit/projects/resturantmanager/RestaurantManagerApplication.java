package hit.projects.resturantmanager;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "hit.projects.resturantmanager.repository")
@EnableAsync
public class RestaurantManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestaurantManagerApplication.class, args);
    }
}

/*
 1. Add DTO objects.
 15. add Documentation.
 */