package hit.projects.resturantmanager.repository;

import hit.projects.resturantmanager.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
}
