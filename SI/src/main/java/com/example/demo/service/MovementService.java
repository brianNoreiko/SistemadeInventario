package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import com.example.demo.models.Movement;
import com.example.demo.repository.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class MovementService {
    @Autowired
    MovementRepository movementRepository;

    public Movement create(Movement movement){
        movement.getProduct().setUnits(movement.getProduct().getUnits()+movement.getQuantity());
        return movementRepository.save(movement);
    }

    public List<Movement> getAllMovements(){
        return movementRepository.findAll();
    }

    public Movement getById(Integer id){
        return movementRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public List<Movement> findAllByDateIsAfterAndProductId(LocalDateTime date, Integer id){
        return movementRepository.findAllByDateIsAfterAndProductIdOrderByDate(date,id);
    }

    public List<Movement> findAllByDateIsAfter(LocalDateTime date){
        return movementRepository.findAllByDateIsAfterOrderByDate(date);
    }

    public void deleteMovement(Integer id){
        Movement movement = getById(id);
        if(movement != null){
            movementRepository.deleteById(id);
        }else{
            System.out.println("El movimiento con esa ID no existe");
        }
    }
}
