package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.pojo.Manager;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

public interface ManagerService {

    CollectionModel<EntityModel<Manager>> getAllManagers();

    EntityModel<Manager> getManager(int personalId);

    EntityModel<Manager> updateManager(int personalId, Manager managerToUpdate);

    EntityModel<Manager> addNewManager(Manager managerToAdd);

    void deleteManager(int personalId);

    CollectionModel<EntityModel<Manager>> getDutyStatus(boolean isOnDuty);
}
