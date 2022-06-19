package hit.projects.resturantmanager;

import hit.projects.resturantmanager.StudentToturial.Address;
import hit.projects.resturantmanager.StudentToturial.Gender;
import hit.projects.resturantmanager.StudentToturial.Student;
import hit.projects.resturantmanager.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "hit.projects.resturantmanager.repository")
public class ResturantManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResturantManagerApplication.class, args);
    }

//    @Bean
//    CommandLineRunner runner(StudentRepository myStudents){
//        return args -> {
//            Address address = new Address("England","75575","London");
//            Student student = new Student("Matan","Bar","matan@gmail.com",
//                    Gender.MALE,address, List.of("Electric"), BigDecimal.TEN);
//
//            //myStudents.insert(student);
//            //System.out.println(myStudents.findStudentByEmail("matan@gmail.com"));
//            System.out.println(myStudents.findAllByEmail("matan@gmail.com"));
//
//        };
//    }
}
