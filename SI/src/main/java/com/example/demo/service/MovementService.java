package com.example.demo.service;

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
        return movementRepository.save(movement);
    }

    public List<Movement> getAllMovements(){
        return movementRepository.findAll();
    }

    public Movement getById(Integer id){
        return movementRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
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
