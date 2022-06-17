package hit.projects.resturantmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "hit.projects.resturantmanager.repository")
public class ResturantManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResturantManagerApplication.class, args);
    }

}
