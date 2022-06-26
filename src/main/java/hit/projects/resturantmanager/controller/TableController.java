package hit.projects.resturantmanager.controller;

import hit.projects.resturantmanager.pojo.Order;
import hit.projects.resturantmanager.pojo.Table;
import hit.projects.resturantmanager.repository.OrderRepository;
import hit.projects.resturantmanager.service.TableService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/table")
@RestController
@AllArgsConstructor
public class TableController {
    private final TableService tableService;


    @GetMapping
    ResponseEntity<List<Table>> getAllTables() {
        return ResponseEntity.ok(tableService.getAllTables());
    }

    @PutMapping("/{number}")
    ResponseEntity<?> updateOrderList(@PathVariable int number, @RequestBody Order order) {
        tableService.addOrder(number, order);
        return ResponseEntity.ok().build();
    }

}
