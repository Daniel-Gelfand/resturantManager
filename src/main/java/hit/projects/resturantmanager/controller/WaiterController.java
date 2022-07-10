package hit.projects.resturantmanager.controller;

import hit.projects.resturantmanager.pojo.Payment;
import hit.projects.resturantmanager.pojo.Waiter;
import hit.projects.resturantmanager.service.WaiterService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/waiter")
public class WaiterController {
    private final WaiterService waiterService;

    public WaiterController(WaiterService waiterService) {
        this.waiterService = waiterService;
    }

    /**
     *  In this method we return all waiters in DB.
     * @return status ok if everything going well .
     */
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Waiter>>> getAllWaiters() {
        return ResponseEntity.ok(waiterService.getAllWaiters());
    }

    /**
     * In this method we reutrn specific waiter by personal id.
     * @param personalId Except to get valid and exist personal id
     * @return status 200 if everything going well.
     */
    @GetMapping("/{personalId}")
    public ResponseEntity<EntityModel<Waiter>> getWaiter(@PathVariable int personalId) {
        return ResponseEntity.ok(waiterService.getWaiter(personalId));
    }

    /**
     * In this method we return all waiters that are in duty.
     * @param isOnDuty = Except to get valid Duty status.
     * @return status 200.
     */
    @GetMapping("/duty/{isOnDuty}")
    public ResponseEntity<CollectionModel<EntityModel<Waiter>>> getDutyStatus(@PathVariable boolean isOnDuty) {
        return ResponseEntity.ok(waiterService.getDutyStatus(isOnDuty));
    }

    /**
     * In this method we update waiter by his personal id
     * @param personalId -> Expect to get personal id of the waiter.
     * @param waiter -> Expect t o get JSON with details to update to specific waiter.
     * @return status 200, updated the waiter info.
     */
    @PutMapping("/{personalId}")
    public ResponseEntity<EntityModel<Waiter>> updateWaiter(@PathVariable int personalId, @RequestBody Waiter waiter) {
        return ResponseEntity.ok(waiterService.updateWaiter(personalId, waiter));
    }

    /**
     * In this method we add new waiter to the database.
     * @param waiterToAdd -> Expect to get JSON details about waiter.
     * @return status 200, added the waiter to database.
     */
    @PostMapping
    public ResponseEntity<EntityModel<Waiter>> addNewWaiter(@RequestBody Waiter waiterToAdd) {
        return new ResponseEntity<>(waiterService.addNewWaiter(waiterToAdd), HttpStatus.CREATED);
    }

    /**
     * In this method we delete specific waiter by his personal id.
     * @param personalId -> Expect to get personal id of waiter.
     * @return status 202, delete the waiter from database.
     */
    @DeleteMapping("/{personalId}")
    public ResponseEntity<?> deleteWaiter(@PathVariable int personalId) {
        waiterService.deleteWaiter(personalId);
        return ResponseEntity.status(202).build();
    }

    /**
     * In this method, we get info about all waiters the in duty. (DTO)
     * @param isOnDuty -> Expect to get true/false.
     * @return status 200, all the waiters that is on duty or not.
     */
    @GetMapping("/info/{isOnDuty}")
    public ResponseEntity<?> getAllWaitersOnDutyInfo(@PathVariable boolean isOnDuty) {
        return ResponseEntity.ok(waiterService.getAllWaitersOnDutyInfo(isOnDuty));
    }

    /**
     * In this method we get specific info about one waiter.
     * @param personalId -> Expect to get personal id of waiter.
     * @return status 200, the specific waiter info.
     */
    @GetMapping("/{personalId}/info")
    public ResponseEntity<?> waiterInfo(@PathVariable int personalId) {
        return ResponseEntity.ok(waiterService.getWaiterInfo(personalId));
    }

    /**
     * In this method we get specific info about all waiter's. (DTO)
     * @return status 200, all waiter's info.
     */
    @GetMapping("/info")
    public ResponseEntity<?> allWaitersInfo() {
        return ResponseEntity.ok(waiterService.getAllWaitersInfo());
    }


    /**
     * In this method we pay money for bill.
     * @param payment -> Expect to get JSON with payment info. (table number and money)
     * @return status 200, payment confirm.
     */
    @PutMapping("/payBill")
    public ResponseEntity<?> payOrderBill( @RequestBody @NotNull @Validated Payment payment) {

        System.out.println(payment);
        return ResponseEntity.ok(waiterService.payOrderBill(payment));
    }
}
