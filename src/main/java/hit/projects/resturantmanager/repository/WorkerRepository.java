package hit.projects.resturantmanager.repository;

import hit.projects.resturantmanager.pojo.Worker;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorkerRepository extends MongoRepository<Worker, Long> {

}
