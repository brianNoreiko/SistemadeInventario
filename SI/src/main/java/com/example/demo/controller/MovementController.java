package com.example.demo.controller;

import com.example.demo.models.Movement;
import com.example.demo.models.responses.Response;
import com.example.demo.service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.EntityResponse;
import utils.EntityURLBuilder;

import java.util.List;

@RestController
@RequestMapping("/movements")
public class MovementController {
    @Autowired
    MovementService movementService;

    @PostMapping("/")
    public ResponseEntity<Response> createMovement(@RequestBody Movement movement) {
        Movement movementCreated = movementService.create(movement);
        return (ResponseEntity<Response>) ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .location(EntityURLBuilder.buildURL("movements",movementCreated.getId()))
                .body(EntityResponse.messageResponse("Movement created successfully"));
    }

    @GetMapping("/")
    public List<Movement> getAllMovements(){
        return movementService.getAllMovements();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movement> getMovementById(@RequestParam(value = "id") Integer id){
        Movement movement= movementService.getById(id);
        return ResponseEntity.ok(movement);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteById(@RequestParam(value = "id") Integer id){
        movementService.deleteMovement(id);
        return ResponseEntity.accepted().build();
    }
}
