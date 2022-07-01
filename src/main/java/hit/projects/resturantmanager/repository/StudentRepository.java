package hit.projects.resturantmanager.repository;

import hit.projects.resturantmanager.StudentToturial.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StudentRepository extends MongoRepository<Student,String> {

    List<Student> findStudentByEmail(String email);

    List<Student> findAllByEmail(String email);
}
