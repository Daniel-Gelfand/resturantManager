package hit.projects.resturantmanager.configuration;

import hit.projects.resturantmanager.entity.Waiter;
import hit.projects.resturantmanager.entity.Worker;
import hit.projects.resturantmanager.repository.WaiterRepository;
import hit.projects.resturantmanager.repository.WorkerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@Slf4j
public class MongoConfiguration {

    @Bean
    CommandLineRunner seedDatabase(WaiterRepository myWaiters) {
        return args -> {
            log.info("loggin " + myWaiters.save(new Waiter('12313', "Daniel", "Gelfand", 2500.0, 35.0)));
        };
    }

//        private Long id;
//        private Long personalId;
//        private String firstName;
//        private String lastName;
//        private Double salary;

    //@Autowired

}