package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.assembler.WaiterAssembler;
import hit.projects.resturantmanager.assembler.dto.WaiterDTOAssembler;
import hit.projects.resturantmanager.controller.WaiterController;
import hit.projects.resturantmanager.exception.RestaurantConflictException;
import hit.projects.resturantmanager.exception.RestaurantNotFoundException;
import hit.projects.resturantmanager.pojo.Order;
import hit.projects.resturantmanager.pojo.Payment;
import hit.projects.resturantmanager.pojo.Table;
import hit.projects.resturantmanager.pojo.Waiter;
import hit.projects.resturantmanager.pojo.dto.WaiterDTO;
import hit.projects.resturantmanager.repository.WaiterRepository;
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
public class WaiterServiceImpl implements WaiterService {
    @Autowired
    private WaiterRepository waiterRepository;
    @Autowired
    private WaiterAssembler waiterAssembler;
    @Autowired
    private WaiterDTOAssembler waiterDTOAssembler;
    @Autowired
    private OrderService orderService;
    @Autowired
    private TableService tableService;


    /**
     * This method Return all Waiters with links (to him self and to all waiters).
     * @return collection model of all waiters.
     */
    @Override
    public CollectionModel<EntityModel<Waiter>> getAllWaiters() {
        List<Waiter> waiters = waiterRepository.findAll();
        List<EntityModel<Waiter>> waitersEntityModelList = waiters.stream().
                map(waiterAssembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(waitersEntityModelList,
                linkTo(methodOn(WaiterController.class).getAllWaiters()).withSelfRel());
    }

    /**
     * In this method we return specific waiter by personal id.
     * @param personalId Except to get valid and exist personal id
     * @return model of specific waiter.
     */
    @Override
    public EntityModel<Waiter> getWaiter(int personalId) {
        Waiter waiter = waiterRepository.findByPersonalId(personalId).
                orElseThrow(() -> new RestaurantNotFoundException(
                        (String.format(Constant.NOT_FOUND_MESSAGE, "personal id", personalId))));

        return waiterAssembler.toModel(waiter);
    }

    /**
     * In this method we return all waiters that are in duty.
     * @param onDuty = Except to get valid Duty status.
     * @return collection model of waiters that on duty.
     */
    @Override
    public CollectionModel<EntityModel<Waiter>> getDutyStatus(boolean onDuty) {
        List<Waiter> waiters = waiterRepository.getAllByOnDuty(onDuty);
        List<EntityModel<Waiter>> waitersEntityModelList = waiters.stream().map(waiterAssembler::toModel).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(waiters)) {
            throw new RestaurantNotFoundException(
                    (String.format(Constant.NOT_FOUND_MESSAGE, "manager on duty", onDuty)));
        }

        return CollectionModel.of(waitersEntityModelList, linkTo(methodOn(WaiterController.class)
                .getAllWaiters()).withSelfRel());
    }

    /**
     * In this method we update waiter by his personal id
     * @param personalId -> Expect to get personal id of the waiter.
     * @param waiter -> Expect t o get JSON with details to update to specific waiter.
     * @return model of waiter that updated.
     */
    @Override
    public EntityModel<Waiter> updateWaiter(int personalId, Waiter waiter) {
        return waiterRepository.findByPersonalId(personalId)
                .map(waiterToUpdate -> waiterAssembler.toModel(waiterRepository.save(waiterToUpdate.update(waiter))))
                .orElseGet(() -> {
                    waiter.setPersonalId(personalId);
                    return waiterAssembler.toModel(waiterRepository.save(waiter));
                });
    }

    /**
     * In this method we add new waiter to the database.
     * @param waiterToAdd -> Expect to get JSON details about waiter.
     * @return model of waiter to add.
     */
    @Override
    public EntityModel<Waiter> addNewWaiter(Waiter waiterToAdd) {

        if (!waiterRepository.existsByPersonalId(waiterToAdd.getPersonalId())) {
            List<Table> tables = tableService.getAllTables().getContent().stream().toList().stream().map(EntityModel::getContent).toList();

            tableService.addWaiterToTables(waiterToAdd);
            waiterToAdd.setTableList(tables);
            return waiterAssembler.toModel(waiterRepository.save(waiterToAdd));
        }

        throw new RestaurantConflictException(
                (String.format(Constant.ALREADY_EXISTS_MESSAGE, "waiter", waiterToAdd.getFirstName())));

    }

    /**
     * In this method we delete specific waiter by his personal id.
     * @param personalId -> Expect to get personal id of waiter.
     */
    @Override
    public void deleteWaiter(int personalId) {
        if (waiterRepository.existsByPersonalId(personalId)) {
            waiterRepository.deleteByPersonalId(personalId);
        }else {
            throw new RestaurantNotFoundException(
                    (String.format(Constant.NOT_FOUND_MESSAGE, "personal id", personalId)));
        }
    }
    /**
     * In this method, we get info about all waiters the in duty. (DTO)
     * @param isOnDuty -> Expect to get true/false.
     * @return collection model of waiters that on duty dto info.
     */
    @Override
    public CollectionModel<EntityModel<WaiterDTO>> getAllWaitersOnDutyInfo(boolean isOnDuty) {
        CollectionModel<EntityModel<WaiterDTO>> waiters = CollectionModel
                .of(waiterDTOAssembler
                        .toCollectionModel(StreamSupport
                                .stream(waiterRepository.getAllByOnDuty(isOnDuty).spliterator(), false)
                                .map(WaiterDTO::new)
                                .collect(Collectors.toList())));

        if (waiters.getContent().isEmpty()) {
            throw new RestaurantNotFoundException(String.format(Constant.NOT_FOUND_MESSAGE, "isOnDuty", isOnDuty));
        }

        return waiters;
    }

    /**
     * In this method we get specific info about one waiter.
     * @param personalId -> Expect to get personal id of waiter.
     * @return model of specific waiter dto info.
     */
    @Override
    public EntityModel<WaiterDTO> getWaiterInfo(int personalId) {

        return waiterRepository.findByPersonalId(personalId).map(WaiterDTO::new).map(waiterDTOAssembler::toModel).orElseThrow(()-> new RestaurantNotFoundException(String.format(Constant.NOT_FOUND_MESSAGE, "personal id", personalId)));
    }

    /**
     * In this method we get specific info about all waiter's. (DTO)
     * @return collection model of waiters dto info.
     */
    @Override
    public CollectionModel<EntityModel<WaiterDTO>> getAllWaitersInfo() {
        CollectionModel<EntityModel<WaiterDTO>> waiters = CollectionModel
                .of(waiterDTOAssembler
                        .toCollectionModel(StreamSupport
                                .stream(waiterRepository.findAll().spliterator(), false)
                                .map(WaiterDTO::new)
                                .collect(Collectors.toList())));

        if (waiters.getContent().isEmpty()) {
            throw new RestaurantNotFoundException(Constant.NOT_FOUND_MESSAGE);
        }

        return waiters;
    }

    /**
     * In this method we pay money for bill.
     * @param payment -> Expect to get JSON with payment info. (table number and money)
     * @return model of payment,
     */
    @Override
    public EntityModel<Order> payOrderBill(Payment payment) {
        return orderService.payOrderBill(payment.getOrderNumber(), payment.getPayment());
    }


    @Override
    public void addTableToWaiters(Table table) {
        List<Waiter> waiters = waiterRepository.findAll();

        for (Waiter waiter : waiters) {
            waiter.getTableList().add(table);
            waiterRepository.save(waiter);
        }
    }
}
