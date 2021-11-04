package com.example.demo.controller;

import com.example.demo.models.Movement;
import com.example.demo.models.Product;
import com.example.demo.models.Stat;
import com.example.demo.models.dto.MovementDTO;
import com.example.demo.models.responses.Response;
import com.example.demo.service.MovementService;
import com.example.demo.service.ProductService;
import com.example.demo.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.EntityResponse;
import utils.EntityURLBuilder;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.POST, RequestMethod.GET})
@RestController
@RequestMapping("/movements")
public class MovementController {
    final MovementService movementService;
    final ProductService productService;
    final StatService statService;

    @Autowired
    public MovementController(MovementService movementService, ProductService productService, StatService statService) {
        this.movementService = movementService;
        this.productService = productService;
        this.statService = statService;
    }

    @PostMapping
    public ResponseEntity<Response> createMovement(@RequestBody MovementDTO movement) {
        Product pr = productService.getByBarcode(movement.getBarcode());
        Movement newMov = Movement.builder().date(LocalDateTime.now()).product(pr).quantity(movement.getQuantity()).build();
        Stat stats = statService.getStats(pr);
        if (pr.getUnits() + newMov.getQuantity() <= stats.getReorder()){
            //TODO SI LA CANTIDAD QUE QUEDA DESPUES DEL RETIRO ES IGUAL O MENOR AL PUNTO DE REORDEN ENVIAR MAIL DE AVISO
        }
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .location(EntityURLBuilder.buildURL("movements", movementService.create(newMov).getId()))
                    .body(EntityResponse.messageResponse("Movement created successfully"));
    }

    @GetMapping
    public List<Movement> getAllMovements() {
        return movementService.getAllMovements();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movement> getMovementById(@PathVariable(value = "id") Integer id) {
        Movement movement = movementService.getById(id);
        return ResponseEntity.ok(movement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteById(@PathVariable(value = "id") Integer id) {
        movementService.deleteMovement(id);
        return ResponseEntity.accepted().build();
    }
}
