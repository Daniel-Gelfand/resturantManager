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

    private final ManagerRepository managerRepository;
    private final ManagerAssembler managerAssembler;
    private final ManagerDTOAssembler managerDTOAssembler;

    public ManagerServiceImpl(ManagerRepository managerRepository, ManagerAssembler managerAssembler,ManagerDTOAssembler managerDTOAssembler) {
        this.managerRepository = managerRepository;
        this.managerAssembler = managerAssembler;
        this.managerDTOAssembler = managerDTOAssembler;
    }

    /**
     *
     * @return
     */
    @Override
    public CollectionModel<EntityModel<Manager>> getAllManagers() {
        List<EntityModel<Manager>> managerList = managerRepository.findAll()
                .stream().map(managerAssembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(managerList, linkTo(methodOn(ManagerController.class)
                .getAllManagers()).withSelfRel());
    }

    /**
     *
     * @param personalId
     * @return
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
     *
     * @param personalId
     * @param manager
     * @return
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
     *
     * @param managerToAdd
     * @return
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
     *
     * @param personalId
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
     *
     * @param isOnDuty
     * @return
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

    @Override
    public CollectionModel<EntityModel<ManagerDTO>> getAllManagerInfo() {
        return CollectionModel.of(managerDTOAssembler
                .toCollectionModel(StreamSupport
                        .stream(managerRepository.findAll().spliterator(), false)
                        .map(ManagerDTO::new)
                        .collect(Collectors.toList())));
    }

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



