package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.assembler.ManagerAssembler;
import hit.projects.resturantmanager.assembler.dto.ManagerDTOAssembler;
import hit.projects.resturantmanager.controller.ManagerController;
import hit.projects.resturantmanager.exception.RestaurantConflictException;
import hit.projects.resturantmanager.exception.RestaurantNotFoundException;
import hit.projects.resturantmanager.pojo.Manager;
import hit.projects.resturantmanager.pojo.dto.ManagerDTO;
import hit.projects.resturantmanager.pojo.dto.MenuItemDTO;
import hit.projects.resturantmanager.repository.ManagerRepository;
import hit.projects.resturantmanager.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private ManagerAssembler managerAssembler;
    @Autowired
    private ManagerDTOAssembler managerDTOAssembler;


    /**
     * In this method we get all managers in our database.
     * @return collection model of managers.
     */
    @Override
    public CollectionModel<EntityModel<Manager>> getAllManagers() {
        List<EntityModel<Manager>> managerList = managerRepository.findAll()
                .stream().map(managerAssembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(managerList, linkTo(methodOn(ManagerController.class)
                .getAllManagers()).withSelfRel());
    }

     /**
     * In his method we get manager details by his id.
     * @param personalId -> Expect to get personal id of manager.
     * @return model of specific manager.
     */
    @Override
    public EntityModel<Manager> getManager(int personalId) {
        Manager manager = managerRepository
                .getManagerByPersonalId(personalId)
                .orElseThrow(() ->
                        new RestaurantNotFoundException(
                                (String.format(Constant.NOT_FOUND_MESSAGE, "personal id", personalId))));

        return managerAssembler.toModel(manager);
    }

    /**
     * In this method we update specific manager.
     * @param personalId -> Expect to get personal id of manager.
     * @param manager -> Expect to GET json with details to update the manager.
     * @return model of manager to update
     */
    @Override
    public EntityModel<Manager> updateManager(int personalId, Manager manager) {
        return managerRepository.findById(personalId)
                .map(managerToUpdate -> managerAssembler
                        .toModel(managerRepository
                                .save(managerToUpdate.update(manager))))
                .orElseGet(() -> managerAssembler.toModel(managerRepository.save(manager)));
    }

    /**
     * In this method we add new manager to our database.
     * @param managerToAdd -> Expect to GET json with details to update the manager.
     * @return model of manager to add.
     */
    @Override
    public EntityModel<Manager> addNewManager(Manager managerToAdd) {

        if (!managerRepository.existsByPersonalId(managerToAdd.getPersonalId())) {
            return managerAssembler.toModel(managerRepository.save(managerToAdd));
        }
        throw new RestaurantConflictException(
                (String.format(Constant.ALREADY_EXISTS_MESSAGE, "manager", managerToAdd.getFirstName())));
    }

    /**
     * In this method we delete manager by id in our database.
     * @param personalId -> Expect to get personal id of manager.
     */
    @Override
    public void deleteManager(int personalId) {
        if (!managerRepository.existsByPersonalId(personalId)) {
            throw new RestaurantNotFoundException(
                    (String.format(Constant.NOT_FOUND_MESSAGE, "personal id", personalId)));
        }
        managerRepository.deleteById(personalId);
    }

    /**
     * In this method we get managers status if they  on duty or not.
     * @param isOnDuty -> Expect to get true or false.
     * @return collection model of managers on duty.
     */
    @Override
    public CollectionModel<EntityModel<Manager>> getDutyStatus(boolean isOnDuty) {
        List<Manager> managers = managerRepository.getAllByOnDuty(isOnDuty);
        List<EntityModel<Manager>> managersEntityModelList = managers.stream()
                .map(managerAssembler::toModel).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(managers)) {
            throw new RestaurantNotFoundException(
                    (String.format(Constant.NOT_FOUND_MESSAGE, "manager on duty", isOnDuty)));
        }

        return CollectionModel.of(managersEntityModelList, linkTo(methodOn(ManagerController.class)
                .getAllManagers()).withSelfRel());
    }


    /**
     * In this method we get all manager's info with specific details (fullname,isOnDuty).
     * @return collection model of managers dto info.
     */
    @Override
    public CollectionModel<EntityModel<ManagerDTO>> getAllManagerInfo() {
        return CollectionModel.of(managerDTOAssembler
                .toCollectionModel(StreamSupport
                        .stream(managerRepository.findAll().spliterator(), false)
                        .map(ManagerDTO::new)
                        .collect(Collectors.toList())));
    }

    /**
     * In this method we get manager info with specific details (fullname,isOnDuty).
     * @param personalId -> Expect to get personal id of manager.
     * @return model of manager dto info.
     */
    @Override
    public EntityModel<ManagerDTO> getManagerInfo(int personalId) {
        return managerRepository
                .getManagerByPersonalId(personalId)
                .map(ManagerDTO::new)
                .map(managerDTOAssembler::toModel)
                .orElseThrow(() -> new RestaurantNotFoundException(
                        (String.format(Constant.NOT_FOUND_MESSAGE, "personalId", personalId))));
    }


}



