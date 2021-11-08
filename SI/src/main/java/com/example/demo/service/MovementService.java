package com.example.demo.service;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.demo.models.Movement;
import com.example.demo.repository.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class MovementService {
    private final MovementRepository movementRepository;
    private final ProductService productService;

    @Autowired
    public MovementService(MovementRepository movementRepository, ProductService productService) {
        this.movementRepository = movementRepository;
        this.productService = productService;
    }

    public Movement create(Movement movement){
        movement.getProduct().setUnits(movement.getProduct().getUnits()+movement.getQuantity());
        productService.checkReorderQ(movement.getProduct(),movement);
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

    public Movement findLastEntranceGreaterThan(Integer productId,Integer greaterThan){
        Optional<Movement> optionalMovement=movementRepository.findTopByIdAndQuantityGreaterThan(productId,greaterThan);
        return optionalMovement.orElse(null);
    }

    public List<Movement> findAllExtractionsByProductIdMonthly(Integer productId){
        return movementRepository.findAllByDateIsAfterAndProductIdOrderByDate(LocalDateTime.now().minus(Period.ofMonths(1)), productId).stream()
                .filter(movement -> movement.getQuantity() < 0)
                .collect(Collectors.toList());
    }
}
