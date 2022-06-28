package hit.projects.resturantmanager.controller;

import hit.projects.resturantmanager.pojo.Order;
import hit.projects.resturantmanager.pojo.Table;
import hit.projects.resturantmanager.repository.OrderRepository;
import hit.projects.resturantmanager.service.TableService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@RequestMapping("/table")
@RestController
@AllArgsConstructor
public class TableController {

    private final TableService tableService;


    @GetMapping
    public ResponseEntity<List<Table>> getAllTables() {
        return ResponseEntity.ok(tableService.getAllTables());
    }

    @GetMapping("/{number}")
    public ResponseEntity<EntityModel<Table>> getTable(@PathVariable int number) {
        return ResponseEntity.ok().body(tableService.getTable(number));
    }

    @PutMapping("/{number}")
    public ResponseEntity<?> updateOrderList(@PathVariable int number, @RequestBody Order order) {
        tableService.addOrder(number, order);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> createNewTable(@RequestBody Table table) {
        tableService.createTable(table);
        return ResponseEntity.ok().body(table);
    }

    @DeleteMapping("/{number}")
    public ResponseEntity<?> deleteTable(@PathVariable int number) {

        return ResponseEntity.ok().body("Table number " + number + " is deleted.");
    }
}
