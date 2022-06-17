package hit.projects.resturantmanager.repository;

import hit.projects.resturantmanager.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu,Long> {
}
